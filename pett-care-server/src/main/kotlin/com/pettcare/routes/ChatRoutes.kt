package com.pettcare.routes

import com.pettcare.chats.repository.ChatRepository
import com.pettcare.chats.requests.GetChatRequest
import com.pettcare.core.BaseResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Route.getUserChats(repository: ChatRepository){
    authenticate {
        get("/chats") {
            val id = call.request.queryParameters["id"]
            if(id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    BaseResponse.ErrorResponse(
                        data = null,
                        message = "id cannot be null"
                    )
                )
                return@get
            }
            val result = repository.getAllUserChats(id)
            call.respond(result.statusCode, result)
        }
    }
}

fun Route.getChat(repository: ChatRepository){
    authenticate {
        post("/chat") {
            val params = call.receive<GetChatRequest>()
            val result = repository.getChat(params)
            call.respond(result.statusCode, result)
        }
    }
}

fun Application.chatRoutes() {
    val repository by inject<ChatRepository>()
    routing {
        getChat(repository)
        getUserChats(repository)
    }
}
