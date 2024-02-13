package ch.awae.paas.service.auth.security

import ch.awae.paas.service.auth.service.*
import jakarta.servlet.*
import jakarta.servlet.http.*
import org.springframework.security.authentication.*
import org.springframework.security.core.*
import org.springframework.security.core.authority.*
import org.springframework.security.core.context.*
import org.springframework.stereotype.*
import org.springframework.web.filter.*

@Component
class HttpAuthorizationTokenFilter(val securityService: SecurityService) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val ctx = SecurityContextHolder.getContext()
        val authHeader = request.getHeader("Authorization")

        val auth: Authentication? = if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // BEARER TOKEN FOR "REAL USER" ACCOUNT AUTH
            val tokenString = authHeader.substring(7)
            securityService.authenticateToken(tokenString)?.let {
                val authorities = if (it.admin) {
                    listOf("user", "admin")
                } else {
                    listOf("user")
                }
                UsernamePasswordAuthenticationToken(
                    it.username,
                    tokenString,
                    authorities.map(::SimpleGrantedAuthority)
                )
            }
        } else {
            null
        }

        if (auth != null) {
            logger.info("authenticated user '${auth.principal}' for ${request.method} ${request.requestURI}")
        } else {
            logger.info("no authentication provided for ${request.method} ${request.requestURI}")
        }

        ctx.authentication = auth

        filterChain.doFilter(request, response)
    }

}
