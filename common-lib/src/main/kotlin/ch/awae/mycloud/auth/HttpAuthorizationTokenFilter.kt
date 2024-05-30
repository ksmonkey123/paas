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

        val auth = if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // BEARER TOKEN FOR "REAL USER" ACCOUNT AUTH
            val tokenString = authHeader.substring(7)
            authServiceClient.authenticateToken(tokenString)
        } else {
            null
        }

        if (auth != null) {
            logger.info("authenticated user '${auth.username}' for ${request.method} ${request.requestURI}")
        } else if (!request.requestURI.startsWith("/actuator/")) {
            // do not log actuator endpoints
            logger.info("no authentication provided for ${request.method} ${request.requestURI}")
        }

        AuthInfo.impersonate(auth)

        filterChain.doFilter(request, response)
    }

}
