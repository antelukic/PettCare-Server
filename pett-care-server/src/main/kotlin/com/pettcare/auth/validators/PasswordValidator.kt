package com.pettcare.auth.validators

interface PasswordValidator {

    operator fun invoke(password: String): Boolean
}

private const val PASSWORD_VALIDATION = "^(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]{8,}\$"

class PasswordValidatorImpl : PasswordValidator {

    override fun invoke(password: String): Boolean = PASSWORD_VALIDATION.toRegex().matches(password)
}