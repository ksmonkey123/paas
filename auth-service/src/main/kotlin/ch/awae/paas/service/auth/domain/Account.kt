package ch.awae.paas.service.auth.domain

import com.fasterxml.jackson.annotation.*
import jakarta.persistence.*
import org.springframework.data.jpa.repository.*
import org.springframework.data.jpa.repository.Query

@Entity
class Account(
    @Column(updatable = false, unique = true)
    val username: String,
    @JsonIgnore
    var password: String,
    var enabled: Boolean = true,
    var admin: Boolean = false,
) : IdBaseEntity()

interface AccountRepository : JpaRepository<Account, Long> {

    @Query("select count(*) from Account a where a.admin")
    fun countAdmins(): Long

    @Query("select a from Account a where a.username = :username and a.enabled")
    fun findActiveByUsername(username: String): Account?

    @Query("select t.account from AuthToken t where t.tokenString = :tokenString and t.account.enabled")
    fun findActiveByTokenString(tokenString: String): Account?

    fun findByUsername(username: String): Account?

    fun existsByUsername(username: String): Boolean

}