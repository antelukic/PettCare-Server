package com.pettcare

import com.pettcare.db.DatabaseFactory
import com.pettcare.plugins.configureCors
import com.pettcare.plugins.configureDependencyInjection
import com.pettcare.plugins.configureHTTP
import com.pettcare.plugins.configureMonitoring
import com.pettcare.plugins.configureRouting
import com.pettcare.plugins.configureSecurity
import com.pettcare.plugins.configureSerialization
import com.pettcare.plugins.configureSockets
import io.ktor.server.application.Application

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init(environment.config)
    configureDependencyInjection()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureSecurity()
    configureSockets()
    configureRouting()
    configureCors()
}