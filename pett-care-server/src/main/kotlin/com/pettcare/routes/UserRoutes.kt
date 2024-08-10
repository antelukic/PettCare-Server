package com.pettcare.routes

import com.pettcare.core.BaseResponse
import com.pettcare.user.repository.UserRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Route.getUserById(repository: UserRepository) {
    authenticate {
        get ("/user") {
            val id = call.request.queryParameters["id"]
            if(id.isNullOrBlank()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    BaseResponse.ErrorResponse(
                        data = null,
                        message = "id cannot be null"
                    )
                )
                return@get
            }
            val result = repository.getUserById(id)
            call.respond(result.statusCode, result)
        }
    }
}

fun Route.searchUsers(repository: UserRepository){
    authenticate {
        get("/users") {
            val pageSize = call.request.queryParameters["pageSize"]
            val pageNumber = call.request.queryParameters["pageNumber"]
            val query = call.request.queryParameters["query"]
            if(query == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    BaseResponse.ErrorResponse(
                        data = null,
                        message = "query cannot be null"
                    )
                )
                return@get
            }
            val result = repository.searchUser(query, pageSize?.toIntOrNull(), pageNumber?.toLongOrNull())
            call.respond(result.statusCode, result)
        }
    }
}

fun Application.userRoutes() {
    val userRepository by inject<UserRepository>()
    routing {
        getUserById(userRepository)
        searchUsers(userRepository)
    }
}