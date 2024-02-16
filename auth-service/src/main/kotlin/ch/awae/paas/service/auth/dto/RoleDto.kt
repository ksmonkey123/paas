package ch.awae.paas.service.auth.dto

import ch.awae.paas.service.auth.domain.Role

data class RoleDto(
    val name: String,
    val description: String?,
    val enabled: Boolean,
) {
    constructor(role: Role) : this(role.name, role.description, role.enabled)
}