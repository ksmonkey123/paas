package ch.awae.paas.service.audit.model

import ch.awae.paas.db.*
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository

@Entity
class MethodParameter(
    @ManyToOne
    val logEntry: LogEntry,
    val position: Int,
    val name: String?,
    val value: String?,
) : IdBaseEntity()

interface MethodParameterRepository : JpaRepository<MethodParameter, Long>