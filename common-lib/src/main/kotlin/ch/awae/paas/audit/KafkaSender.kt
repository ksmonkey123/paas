package ch.awae.paas.audit

import org.springframework.beans.factory.annotation.*
import org.springframework.kafka.core.*
import org.springframework.stereotype.*
import java.util.concurrent.*

@Component
class KafkaSender (
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    @Value("\${paas.audit.kafka.topic}") val kafkaTopic: String,
){

    private val executor = Executors.newSingleThreadExecutor()

    fun send(auditLogEntry: AuditLogEntry) {
        executor.submit {
            kafkaTemplate.send(kafkaTopic, auditLogEntry)
        }
    }

}