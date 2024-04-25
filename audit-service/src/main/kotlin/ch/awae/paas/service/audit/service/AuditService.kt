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
    private val methodParameterRepository: MethodParameterRepository,
) {

    private val logger = createLogger()

    fun write(entry: AuditLogEntry) {
        if (logEntryRepository.existsByTraceId(entry.traceId)) {
            logger.warn("duplicate log entry for traceId=${entry.traceId}")
            return
        }
        val logEntry = LogEntry(
            entry.timestampStart.toLocalDateTime(),
            entry.timestampEnd.toLocalDateTime(),
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
        val savedEntry = logEntryRepository.save(logEntry)
        val params = entry.method.parameters.map {
            MethodParameter(
                savedEntry,
                it.position,
                it.name,
                it.value,
            )
        }
        methodParameterRepository.saveAll(params)
    }

}