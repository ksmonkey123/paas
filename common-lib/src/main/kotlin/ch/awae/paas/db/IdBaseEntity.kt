package ch.awae.paas.db

import ch.awae.paas.*
import jakarta.persistence.*

@MappedSuperclass
abstract class IdBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long = 0

    override fun equals(other: Any?) = equalByField(other, IdBaseEntity::id)

    override fun hashCode() = id.hashCode()

}