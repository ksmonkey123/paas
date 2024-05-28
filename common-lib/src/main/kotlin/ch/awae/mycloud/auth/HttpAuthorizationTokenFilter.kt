package ch.awae.mycloud.auth

import jakarta.servlet.*
import jakarta.servlet.http.*
import org.springframework.security.authentication.*
import org.springframework.security.core.*
import org.springframework.security.core.authority.*
import org.springframework.security.core.context.*
import org.springframework.stereotype.*
import org.springframework.web.filter.*

@Component
class HttpAuthorizationTokenFilter(val authServiceClient: AuthService) : OncePerRequestFilter() {

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
            authServiceClient.authenticateToken(tokenString)?.let {
                UsernamePasswordAuthenticationToken(
                    it.username,
                    it,
                    it.roles.map(::SimpleGrantedAuthority)
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
