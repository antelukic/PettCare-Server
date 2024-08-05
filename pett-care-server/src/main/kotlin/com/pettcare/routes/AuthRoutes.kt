package com.pettcare.routes

import com.pettcare.auth.repository.UserRepository
import com.pettcare.auth.requests.AuthRequest
import com.pettcare.auth.requests.RegisterUser
import com.pettcare.auth.security.token.TokenConfig
import com.pettcare.core.BaseResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.register(
    repository: UserRepository,
    tokenConfig: TokenConfig
) {
    post("/register") {
        val params = call.receive<RegisterUser>()
        val result = repository.registerUser(params, tokenConfig)
        call.respond(result.statusCode, result)
    }
}


fun Route.login(
    repository: UserRepository,
    tokenConfig: TokenConfig
) {
    post("/login") {
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val result = repository.loginUser(params = request, tokenConfig)
        call.respond(result.statusCode, result)
    }
}

fun Route.authenticate() {
    authenticate {
        get("/authenticate") {
            call.respond(HttpStatusCode.OK, BaseResponse.SuccessResponse(data = true))
        }
    }
}

fun Application.authRoutes() {
    val tokenConfig by inject<TokenConfig>()
    val userRepository by inject<UserRepository>()
    routing {
        register(
            repository = userRepository,
            tokenConfig = tokenConfig
        )
        login(
            repository = userRepository,
            tokenConfig = tokenConfig
        )
        authenticate()
    }
}
