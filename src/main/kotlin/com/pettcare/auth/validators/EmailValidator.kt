package com.pettcare.auth.validators

interface EmailValidator {

    operator fun invoke(email: String): Boolean
}

private const val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

class EmailValidatorImpl: EmailValidator {

    override fun invoke(email: String) = EMAIL_REGEX.toRegex().matches(email)
}