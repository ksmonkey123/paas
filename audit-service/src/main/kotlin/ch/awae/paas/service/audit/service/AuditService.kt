package ch.awae.paas.service.audit.service

import ch.awae.paas.*
import ch.awae.paas.audit.*
import ch.awae.paas.service.audit.model.*
import jakarta.transaction.*
import org.springframework.stereotype.*

@Transactional
@Service
class AuditService(
    private val logEntryRepository: LogEntryRepository,
) {

    private val logger = createLogger()

    fun write(entry: AuditLogEntry) {
        if (logEntryRepository.existsByTraceId(entry.traceId)) {
            logger.warn("duplicate log entry for traceId=${entry.traceId}")
            return
        }
        val logEntry = LogEntry(
            Timing(
                entry.timing.start.toLocalDateTime(),
                entry.timing.end.toLocalDateTime(),
            ),
            entry.traceId,
            entry.service,
            entry.username,
            entry.request?.let {
                Request(
                    it.verb,
                    it.path,
                )
            },
            entry.method.let {
                Method(
                    it.component,
                    it.method,
                    it.error,
                )
            },
        )

        logEntry.method.parameter.putAll(entry.method.parameters)

        logEntryRepository.save(logEntry)
    }

}