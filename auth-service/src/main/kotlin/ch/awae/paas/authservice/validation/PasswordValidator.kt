package ch.awae.paas.authservice.validation

import jakarta.validation.*

class PasswordValidator : ConstraintValidator<ValidPassword, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value == null) return true
        if (value.length < 10) return false

        return true
    }

}