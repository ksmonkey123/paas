package ch.awae.mycloud.service.auth.service

import ch.awae.mycloud.auth.*
import ch.awae.mycloud.service.auth.domain.*
import jakarta.transaction.*
import org.springframework.stereotype.*

@Service
@Transactional
class AuthenticationService(
    private val accountRepository: AccountRepository
) : AuthService {

    fun createAuthInfo(account: Account, tokenString: String) = AuthInfo(
        account.username,
        account.admin,
        account.roles
            .filter { it.enabled }
            .map { it.name }
            .let { roles ->
                if (account.admin) {
                    roles + arrayOf("admin", "user")
                } else {
                    roles + "user"
                }
            },
        tokenString
    )

    override fun authenticateToken(tokenString: String): AuthInfo? {
        return accountRepository.findActiveByTokenString(tokenString)?.let { account ->
            createAuthInfo(account, tokenString)
        }
    }

}