package ch.awae.paas.auth

import ch.awae.paas.rest.*
import org.springframework.http.*
import org.springframework.stereotype.*
import org.springframework.web.client.*
import org.springframework.web.client.HttpClientErrorException.*

@Service
class AuthServiceClient(
    @Internal
    private val http: RestTemplate,
) {

    fun authenticateToken(tokenString: String): AuthInfo? {
        val headers = HttpHeaders()
        headers.setBearerAuth(tokenString)
        val entity = HttpEntity(null, headers)
        return try {
            http.exchange<AuthInfoDto>("http://auth-service/account", HttpMethod.GET, entity).body?.let {
                AuthInfo(it.username, it.admin, it.roles, tokenString)
            }
        } catch (e: Unauthorized) {
            null
        }
    }

    private data class AuthInfoDto(
        val username: String,
        val admin: Boolean,
        val roles: List<String>
    )

}
