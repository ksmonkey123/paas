package ch.awae.mycloud.auth

import org.springframework.security.authentication.*
import org.springframework.security.core.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.*

data class AuthInfo(
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

        fun impersonate(info: AuthInfo?) {
            val auth = info?.let {
                UsernamePasswordAuthenticationToken(
                    it.username,
                    it,
                    it.roles.map(::SimpleGrantedAuthority)
                )
            }
            SecurityContextHolder.getContext().authentication = auth
        }

    }
}