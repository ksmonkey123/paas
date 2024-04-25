package ch.awae.paas.audit

import ch.awae.paas.auth.*
import java.sql.*

data class AuditLogEntry(
    val traceId: String,
    val service: String,
    // timing information
    val timestampStart: Timestamp,
    val timestampEnd: Timestamp,
    // security information
    val username: String? = AuthInfo.username,
    // rest request information
    val request: Request? = null,
    // method information
    val method: Method,
) {
    data class Request(val verb: String, val path: String)

    data class Method(
        val component: String,
        val method: String,
        val parameters: List<Parameter>,
        val error: String?,
    ) {
        data class Parameter(
            val position: Int,
            val name: String?,
            val value: String?,
        )
    }
}