package ch.awae.paas.service.audit.model

import ch.awae.paas.db.*
import jakarta.persistence.*
import org.springframework.data.jpa.repository.*
import java.time.*

@Entity
class LogEntry(
    val timestamp: LocalDateTime,
    val traceId: String,
    val serviceName: String,
    val account: String?,
    val request: Request?,
    val method: Method,
) : IdBaseEntity() {
    val rootTraceId = traceId.split('$')[0]
    val parentTraceId = traceId.substring(0, traceId.lastIndexOf('$'))
}

@Embeddable
class Request(
    val verb: String,
    val path: String,
)

@Embeddable
class Method(
    val component: String,
    val method: String,
) {
    @OneToMany(mappedBy = "logEntry")
    val parameter: List<MethodParameter> = emptyList()
}

interface LogEntryRepository : JpaRepository<LogEntry, Long> {

    fun existsByTraceId(traceId: String): Boolean

}