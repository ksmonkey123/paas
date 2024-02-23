package ch.awae.paas.service.auth.service

import ch.awae.paas.auth.*
import ch.awae.paas.service.auth.domain.*
import jakarta.transaction.*
import org.springframework.stereotype.*

@Service
@Transactional
class AuthenticationService(
    private val accountRepository: AccountRepository
) : AuthService {

    override fun authenticateToken(tokenString: String): AuthInfo? {
        return accountRepository.findActiveByTokenString(tokenString)?.let { account ->
            AuthInfo(
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
        }
    }

}