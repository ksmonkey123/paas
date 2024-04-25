package ch.awae.paas.audit

import ch.awae.paas.auth.*
import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.*
import java.lang.reflect.*
import java.sql.*
import kotlin.reflect.full.*
import kotlin.reflect.jvm.*

@Service
class AuditLogService(
    private val kafkaSender: KafkaSender,
    @Value("\${spring.application.name}") val serviceName: String,
) {

    fun recordMethodCall(
        method: Method,
        args: Array<Any?>,
        error: Throwable?,
    ) {
        val params: List<AuditLogEntry.Method.Parameter> = if (args.isNotEmpty()) {
            val paramInfo = method.kotlinFunction?.valueParameters
            if (paramInfo == null) {
                emptyList()
            } else {
                args.mapIndexed { index, arg ->
                    AuditLogEntry.Method.Parameter(
                        index,
                        paramInfo[index].name,
                        arg?.let {
                            if (paramInfo[index].hasAnnotation<NoAudit>()) {
                                "<redacted>"
                            } else {
                                it.toString()
                            }
                        }
                    )
                }
            }
        } else {
            emptyList()
        }

        val request = TraceInformation.requestInfo?.let { AuditLogEntry.Request(it.verb, it.path) }

        val entry = AuditLogEntry(
            TraceInformation.traceId!!,
            serviceName,
            TraceInformation.startTimestamp!!,
            Timestamp(System.currentTimeMillis()),
            AuthInfo.username,
            request,
            AuditLogEntry.Method(
                method.declaringClass.simpleName,
                method.name,
                params,
                error?.toString()
            )
        )

        kafkaSender.send(entry)
    }

}