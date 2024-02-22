package ch.awae.paas.service.auth.facade.rest

import ch.awae.paas.*
import ch.awae.paas.auth.*
import ch.awae.paas.service.auth.service.*
import org.springframework.http.*
import org.springframework.security.access.prepost.*
import org.springframework.web.bind.annotation.*

/**
 * Security relevant endpoints:
 *  - login
 *  - logout
 */
@RestController
class SecurityController(private val securityService: SecurityService) {

    private val logger = createLogger()

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): LoginResponse {
        logger.info("handling login request for ${request.username}")
        val authToken = securityService.login(request.username, request.password)
        return LoginResponse(authToken.tokenString)
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun logout() {
        logger.info("handling logout request for ${AuthInfo.username}")
        securityService.logout(AuthInfo.info!!.token)
    }

    @GetMapping("/authenticate")
    @PreAuthorize("hasAuthority('user')")
    fun getOwnAccountInfo(): AuthInfo = AuthInfo.info!!

    data class LoginRequest(val username: String, val password: String)
    data class LoginResponse(val token: String)

}
