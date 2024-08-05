package com.pettcare.plugins

import com.pettcare.routes.authRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    authRoutes()
}
