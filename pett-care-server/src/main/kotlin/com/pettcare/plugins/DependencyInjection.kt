package com.pettcare.plugins

import com.pettcare.auth.di.authModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules(
            listOf(
                authModule(environment)
            )
        )
    }
}
