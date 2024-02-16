package ch.awae.paas.service.auth.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.*

object AuthInfoHolder {

    private fun getAuthentication(): Authentication? = SecurityContextHolder.getContext().authentication

    val token: String?
        get() = getAuthentication()?.credentials as? String

    val username: String?
        get() = getAuthentication()?.principal as? String


}