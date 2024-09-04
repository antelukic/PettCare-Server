package com.pettcare.routes

import com.pettcare.ChatSession
import com.pettcare.core.BaseResponse
import com.pettcare.message.repository.MessageRepository
import com.pettcare.message.request.SendMessageRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import org.koin.ktor.ext.inject


fun Route.getMessages(repository: MessageRepository) {
    authenticate {
        get("/messages") {
            val chatId = call.request.queryParameters["chatId"]
            if (chatId.isNullOrBlank()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    BaseResponse.ErrorResponse(
                        data = null,
                        message = "chatId cannot be null"
                    )
                )
                return@get
            }
            val result = repository.getAllMessages(chatId)
            call.respond(result.statusCode, result)
        }
    }
}

fun Route.sendMessage(repository: MessageRepository) {
    webSocket("/message") {
        try {
            val session = call.sessions.get<ChatSession>()
            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    repository.sendMessage(
                        this,
                        SendMessageRequest(
                            senderId = session?.userId ?: "TEST",
                            text = frame.readText(),
                            chatId = session?.chatId ?: "TEST"
                        )
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            repository.tryDisconnect(this)
        }
    }
}

fun Application.messageRoutes() {
    val messageRepository by inject<MessageRepository>()
    routing {
        getMessages(messageRepository)
        sendMessage(messageRepository)
    }
}
