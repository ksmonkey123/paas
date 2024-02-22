package ch.awae.paas.auth

import org.springframework.security.core.*
import org.springframework.security.core.context.*

data class AuthInfo internal constructor(
    val username: String,
    val admin: Boolean,
    val roles: List<String>,
    val token: String
) {
    companion object {

        private fun getAuthentication(): Authentication? = SecurityContextHolder.getContext().authentication

        val info: AuthInfo?
            get() = getAuthentication()?.credentials as? AuthInfo

        val username: String?
            get() = info?.username

        val roles: List<String>?
            get() = info?.roles

    }
}