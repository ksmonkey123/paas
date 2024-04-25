package ch.awae.paas.audit

import org.aspectj.lang.*
import org.aspectj.lang.annotation.*
import org.aspectj.lang.reflect.*
import org.springframework.stereotype.*


@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class AuditLog

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class NoAudit

@Aspect
@Component
class AuditLogAspect(private val auditLogService: AuditLogService) {


    @Around(value = "@annotation(ch.awae.paas.audit.AuditLog) || @within(ch.awae.paas.audit.AuditLog)")
    fun auditMethodCall(jointPoint: ProceedingJoinPoint): Any? {
        TraceInformation.push()
        val signature = jointPoint.signature as MethodSignature

        try {
            val result = jointPoint.proceed()
            auditLogService.recordMethodCall(signature.method, jointPoint.args, null)
            return result
        } catch (error: Exception) {
            auditLogService.recordMethodCall(signature.method, jointPoint.args, error)
            throw error
        } finally {
            TraceInformation.pop()
        }
    }

}