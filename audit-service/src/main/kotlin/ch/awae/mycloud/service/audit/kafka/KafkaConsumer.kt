package ch.awae.mycloud.service.audit.kafka

import ch.awae.mycloud.*
import ch.awae.mycloud.audit.*
import ch.awae.mycloud.service.audit.service.*
import org.springframework.kafka.annotation.*
import org.springframework.stereotype.*

@Component
@KafkaListener(topics = ["\${mycloud.audit.kafka.topic}"], containerFactory = "auditKafkaListenerContainerFactory")
class KafkaConsumer(private val svc: AuditService) {

    private val logger = createLogger()

    @KafkaHandler
    fun handleAuditLogEntry(entry: AuditLogEntry) {
        svc.write(entry)
    }

}