package ch.awae.paas.authservice.security

import ch.awae.paas.authservice.service.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.*

object AuthInfo {

    private fun getAuthentication(): Authentication? = SecurityContextHolder.getContext().authentication

    val token: String?
        get() = getAuthentication()?.credentials as? String

    val username: String?
        get() = getAuthentication()?.principal as? String


}