package com.pettcare.plugins

import com.pettcare.auth.di.authModule
import com.pettcare.post.di.postModule
import com.pettcare.user.di.userModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules(
            listOf(
                authModule(environment),
                postModule,
                userModule
            )
        )
    }
}
