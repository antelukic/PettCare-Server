package com.pettcare.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import java.time.*

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(100)
        timeout = Duration.ofSeconds(100)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
}