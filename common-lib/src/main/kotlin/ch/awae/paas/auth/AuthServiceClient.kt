package ch.awae.paas.auth

import com.github.benmanes.caffeine.cache.*
import org.springframework.http.*
import org.springframework.web.client.*
import org.springframework.web.client.HttpClientErrorException.*
import kotlin.time.*
import kotlin.time.Duration.Companion.seconds

class AuthServiceClient(
    private val http: RestTemplate,
) : AuthService {

    init {
        println("auth service client")
    }

    private val cache: LoadingCache<String, AuthInfo> = Caffeine.newBuilder()
        .maximumSize(100)
        .expireAfterWrite(30.seconds.toJavaDuration())
        .build { token -> fetchToken(token) }

    override fun authenticateToken(tokenString: String): AuthInfo? {
        return cache.get(tokenString)
    }

    fun fetchToken(tokenString: String): AuthInfo? {
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
