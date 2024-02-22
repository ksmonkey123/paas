package ch.awae.paas.service.auth.facade.rest

import ch.awae.paas.auth.*
import ch.awae.paas.service.auth.service.*
import ch.awae.paas.service.auth.validation.*
import jakarta.validation.*
import org.springframework.http.*
import org.springframework.security.access.prepost.*
import org.springframework.web.bind.annotation.*

@PreAuthorize("hasAuthority('user')")
@RequestMapping("/account")
@RestController
class AccountSettingsController(
    private val accountService: AccountService,
) {

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changeOwnPassword(@Valid @RequestBody request: ChangePasswordRequest) {
        accountService.changePassword(AuthInfo.username!!, request.oldPassword, request.newPassword)
    }

    data class ChangePasswordRequest(
        val oldPassword: String,
        @field:ValidPasswordFormat val newPassword: String,
    )

}