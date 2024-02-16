package ch.awae.paas.service.auth.dto

import ch.awae.paas.service.auth.domain.*

data class AuthInfoDto(
    val username: String,
    val admin: Boolean,
    val roles: List<String>
) {
    constructor(account: Account) : this(account.username,
        account.admin,
        account.roles
            .filter { it.enabled }
            .map { it.name }
            .let { roles ->
                if (account.admin) {
                    roles + arrayOf("admin", "user")
                } else {
                    roles + "user"
                }
            }
    )
}