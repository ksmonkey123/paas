package ch.awae.paas.authservice.service

import ch.awae.paas.authservice.*
import ch.awae.paas.authservice.domain.*
import ch.awae.paas.authservice.exception.*
import jakarta.transaction.*
import org.springframework.security.crypto.password.*
import org.springframework.stereotype.*

@Service
@Transactional
class SecurityService(
    private val accountRepository: AccountRepository,
    private val authTokenRepository: AuthTokenRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    val logger = createLogger()

    @Throws(BadLoginException::class)
    fun authenticateCredentials(username: String, password: String): Account {
        val account = accountRepository.findActiveByUsername(username)
            ?.takeIf { passwordEncoder.matches(password, it.password) }
            ?: throw BadLoginException()
        logger.info("authenticated account $username")
        return account
    }

    @Throws(BadLoginException::class)
    fun login(username: String, password: String): AuthToken {
        val account = authenticateCredentials(username, password)
        return authTokenRepository.saveAndFlush(AuthToken.buildToken(account))
    }

    fun authenticateToken(tokenString: String): Account? {
        return accountRepository.findActiveByTokenString(tokenString)
    }

    fun logout(token: String) {
        authTokenRepository.deleteByTokenString(token)
    }

}
