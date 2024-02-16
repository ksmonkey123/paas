package ch.awae.paas.service.auth.facade.rest

import ch.awae.paas.service.auth.dto.*
import ch.awae.paas.service.auth.service.*
import ch.awae.paas.service.auth.validation.*
import jakarta.validation.*
import org.springframework.http.*
import org.springframework.security.access.prepost.*
import org.springframework.web.bind.annotation.*

@RequestMapping("/accounts")
@RestController
class UserManagementController(
    private val accountService: AccountService,
) {


    @GetMapping("")
    @PreAuthorize("hasAuthority('admin')")
    fun listAllAccounts(): List<AccountSummaryDto> {
        return accountService.getAccounts()
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasAuthority('admin')")
    @ResponseStatus(HttpStatus.CREATED)
    fun createAccount(@PathVariable username: String, @Valid @RequestBody request: CreateUserRequest): AccountSummaryDto {
        return accountService.createAccount(username, request.password, request.admin)
    }

    @PatchMapping("/{username}")
    @PreAuthorize("hasAuthority('admin')")
    fun editAccount(@PathVariable username: String, @RequestBody request: PatchAccountRequest): AccountSummaryDto {
        return accountService.editAccount(username, request.password, request.admin, request.enabled)
    }

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