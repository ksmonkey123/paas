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
        val params: Map<String, String?> = if (args.isNotEmpty()) {
            val paramInfo = method.kotlinFunction?.valueParameters
            if (paramInfo == null) {
                emptyMap()
            } else {
                args.mapIndexed { index, arg ->
                    Pair(paramInfo[index].name ?: "arg#$index", arg?.let {
                        if (paramInfo[index].hasAnnotation<NoAudit>()) {
                            "<redacted>"
                        } else {
                            it.toString()
                        }
                    })
                }.associate { p -> p }
            }
        } else {
            emptyMap()
        }

        val request = TraceInformation.requestInfo?.let { AuditLogEntry.Request(it.verb, it.path) }

        val entry = AuditLogEntry(
            TraceInformation.traceId!!,
            serviceName,
            AuditLogEntry.Timing(
                TraceInformation.startTimestamp!!,
                Timestamp(System.currentTimeMillis()),
            ),
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