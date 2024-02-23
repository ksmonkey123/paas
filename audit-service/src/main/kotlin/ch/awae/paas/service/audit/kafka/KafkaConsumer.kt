package ch.awae.paas.service.audit.kafka

import ch.awae.paas.*
import ch.awae.paas.audit.*
import ch.awae.paas.service.audit.service.*
import org.springframework.kafka.annotation.*
import org.springframework.stereotype.*

@Component
@KafkaListener(topics = ["\${paas.audit.kafka.topic}"], containerFactory = "auditKafkaListenerContainerFactory")
class KafkaConsumer(private val svc: AuditService) {

    private val logger = createLogger()

    @KafkaHandler
    fun handleAuditLogEntry(entry: AuditLogEntry) {
        svc.write(entry)
    }

}