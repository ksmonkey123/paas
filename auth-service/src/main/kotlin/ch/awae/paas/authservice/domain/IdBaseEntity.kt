package ch.awae.paas.authservice.domain

import ch.awae.paas.authservice.*
import jakarta.persistence.*

@MappedSuperclass
abstract class IdBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long = 0

    override fun equals(other: Any?) = equalByField(other, IdBaseEntity::id)

    override fun hashCode() = id.hashCode()

}