package com.pettcare.auth.di

import com.pettcare.auth.repository.UserRepository
import com.pettcare.auth.repository.UserRepositoryImpl
import com.pettcare.auth.security.hashing.HashingService
import com.pettcare.auth.security.hashing.SHA256HashingService
import com.pettcare.auth.security.token.JwtTokenService
import com.pettcare.auth.security.token.TokenConfig
import com.pettcare.auth.security.token.TokenService
import com.pettcare.auth.service.UserService
import com.pettcare.auth.service.UserServiceImpl
import com.pettcare.auth.validators.EmailValidator
import com.pettcare.auth.validators.EmailValidatorImpl
import com.pettcare.auth.validators.PasswordValidator
import com.pettcare.auth.validators.PasswordValidatorImpl
import io.ktor.server.application.ApplicationEnvironment
import org.koin.dsl.module

fun authModule(environment: ApplicationEnvironment) = module {

    single<UserRepository> {
        UserRepositoryImpl(
            userService = get(),
            tokenService = get(),
            passwordValidator = get(),
            emailValidator = get()
        )
    }

    single<HashingService> { SHA256HashingService() }

    single<UserService> {
        UserServiceImpl(
            hashingService = get()
        )
    }

    single {
        TokenConfig(
            secret = environment.config.property("jwt.secret").getString(),
            issuer = environment.config.property("jwt.issuer").getString(),
            audience = environment.config.property("jwt.audience").getString(),
            expiresIn = environment.config.property("jwt.expiration").getString().toLong(),
        )
    }

    single<PasswordValidator> { PasswordValidatorImpl() }

    single<EmailValidator> { EmailValidatorImpl() }

    single<TokenService> { JwtTokenService() }
}
