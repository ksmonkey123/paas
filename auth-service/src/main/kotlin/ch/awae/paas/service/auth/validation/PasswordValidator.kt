package ch.awae.paas.service.auth.validation

import jakarta.validation.*

class PasswordValidator : ConstraintValidator<ValidPassword, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value == null) return true
        if (value.length < 8) return false

        return true
    }

}