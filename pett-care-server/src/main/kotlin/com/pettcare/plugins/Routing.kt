package com.pettcare.plugins

import com.pettcare.routes.authRoutes
import com.pettcare.routes.postRoutes
import com.pettcare.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    authRoutes()
    postRoutes()
    userRoutes()
}
