package ch.awae.paas.authservice.service

import ch.awae.paas.authservice.*
import ch.awae.paas.authservice.domain.*
import ch.awae.paas.authservice.exception.*
import ch.awae.paas.authservice.validation.*
import jakarta.transaction.*
import org.hibernate.validator.constraints.*
import org.springframework.security.crypto.password.*
import org.springframework.stereotype.*

@Service
@Transactional
class AccountService(
    private val securityService: SecurityService,
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    val logger = createLogger()

    fun findActiveAccount(username: String): Account = accountRepository.findActiveByUsername(username)
        ?: throw ResourceNotFoundException("/accounts/$username?enabled=true")

    fun changePassword(username: String, oldPassword: String, @ValidPassword newPassword: String) {
        val account = securityService.authenticateCredentials(username, oldPassword)
        account.password = passwordEncoder.encode(newPassword)
    }

    fun createAccount(
        @Length(min = 5) username: String,
        @ValidPassword password: String, admin: Boolean
    ): Account {
        if (accountRepository.existsByUsername(username))
            throw ResourceAlreadyExistsException("/accounts/$username")

        val account = Account(
            username,
            passwordEncoder.encode(password),
            true,
            admin
        )

        return accountRepository.save(account)
    }

    fun editAccount(
        @Length(min = 5) username: String,
        @ValidPassword password: String?,
        admin: Boolean?,
        enabled: Boolean?,
    ): Account {
        val account = accountRepository.findByUsername(username)
            ?: throw ResourceNotFoundException("/accounts/$username")

        if (password != null) account.password = passwordEncoder.encode(password)
        if (enabled != null) account.enabled = enabled
        if (admin != null) account.admin = admin

        return accountRepository.save(account)
    }

}
