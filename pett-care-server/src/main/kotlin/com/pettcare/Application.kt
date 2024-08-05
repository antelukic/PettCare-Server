package com.pettcare

import com.pettcare.db.DatabaseFactory
import com.pettcare.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    DatabaseFactory.init(environment.config)
    configureDependencyInjection()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureSecurity()
    configureRouting()
    configureCors()
}