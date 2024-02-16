package ch.awae.paas.service.auth.domain

import jakarta.persistence.*
import org.springframework.data.jpa.repository.*

@Entity
class Role(
    @Column(updatable = false, unique = true)
    val name: String,
    var enabled: Boolean = true,
    var description: String?,
) : IdBaseEntity() {
    @ManyToMany
    @JoinTable(
        name = "account_role",
        joinColumns = [JoinColumn(name = "role_id")],
        inverseJoinColumns = [JoinColumn(name = "account_id")]
    )
    val accounts: MutableSet<Account> = mutableSetOf()
}

interface RoleRepository : JpaRepository<Role, Long> {

    fun findByName(name: String): Role?
    fun deleteByName(role: String)

}