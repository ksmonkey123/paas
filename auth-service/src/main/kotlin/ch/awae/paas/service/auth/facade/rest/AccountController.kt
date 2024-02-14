package ch.awae.paas.service.auth.facade.rest

import ch.awae.paas.service.auth.domain.*
import ch.awae.paas.service.auth.security.*
import ch.awae.paas.service.auth.service.*
import ch.awae.paas.service.auth.validation.*
import jakarta.validation.*
import org.springframework.http.*
import org.springframework.security.access.prepost.*
import org.springframework.web.bind.annotation.*

@RestController
class AccountController(
    private val accountService: AccountService,
) {

    @GetMapping("/account")
    @PreAuthorize("hasAuthority('user')")
    fun getOwnAccountInfo(): Account {
        return accountService.findActiveAccount(AuthInfo.username!!)
    }

    @PatchMapping("/account/password")
    @PreAuthorize("hasAuthority('user')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changeOwnPassword(@Valid @RequestBody request: ChangePasswordRequest) {
        accountService.changePassword(AuthInfo.username!!, request.oldPassword, request.newPassword)
    }

    @GetMapping("/accounts")
    @PreAuthorize("hasAuthority('admin')")
    fun listAllAccounts(): List<Account> {
        return accountService.getAccounts()
    }

    @PutMapping("/accounts/{username}")
    @PreAuthorize("hasAuthority('admin')")
    @ResponseStatus(HttpStatus.CREATED)
    fun createAccount(@PathVariable username: String, @Valid @RequestBody request: CreateUserRequest): Account {
        return accountService.createAccount(username, request.password, request.admin)
    }

    @PatchMapping("/accounts/{username}")
    @PreAuthorize("hasAuthority('admin')")
    fun editAccount(@PathVariable username: String, @RequestBody request: PatchAccountRequest): Account {
        return accountService.editAccount(username, request.password, request.admin, request.enabled)
    }

    data class ChangePasswordRequest(
        val oldPassword: String,
        @field:ValidPasswordFormat val newPassword: String,
    )

    data class CreateUserRequest(
        @field:ValidPasswordFormat val password: String,
        val admin: Boolean,
    )

    data class PatchAccountRequest(
        @field:ValidPasswordFormat val password: String?,
        val admin: Boolean?,
        val enabled: Boolean?,
    )

}