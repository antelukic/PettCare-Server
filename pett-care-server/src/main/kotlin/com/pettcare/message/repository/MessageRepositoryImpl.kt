package com.pettcare.message.repository

import com.pettcare.core.BaseResponse
import com.pettcare.core.BaseResponse.ErrorResponse
import com.pettcare.message.request.SendMessageRequest
import com.pettcare.message.service.MessageService
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close

class MessageRepositoryImpl(private val service: MessageService): MessageRepository {

    override suspend fun sendMessage(socketSession: WebSocketSession, message: SendMessageRequest): BaseResponse<out Any> =
        kotlin.runCatching {
            val response = service.insertMessage(message)
            if(response != null) {
                BaseResponse.SuccessResponse(response)
            } else {
                ErrorResponse(message = "Unable to reach database")
            }
        }.getOrElse {
            it.printStackTrace()
            ErrorResponse(message = it.message.toString())
        }

    override suspend fun getAllMessages(chatId: String): BaseResponse<out Any> =
        kotlin.runCatching {
            val messages = service.getMessages(chatId)
            if(messages != null) {
                BaseResponse.SuccessResponse(messages)
            } else {
                ErrorResponse(message = "Unable to reach database")
            }
        }.getOrElse {
            it.printStackTrace()
            ErrorResponse(message = it.message.toString())
        }

    override suspend fun tryDisconnect(socketSession: WebSocketSession): BaseResponse<out Any> =
        kotlin.runCatching {
            socketSession.close()
            BaseResponse.SuccessResponse(true)
        }.getOrElse {
            it.printStackTrace()
            ErrorResponse(message = it.message.toString())
        }
}