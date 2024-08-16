package com.pettcare.message.repository

import com.pettcare.core.BaseResponse
import com.pettcare.message.request.SendMessageRequest
import io.ktor.websocket.WebSocketSession

interface MessageRepository {

    suspend fun sendMessage(socketSession: WebSocketSession, message: SendMessageRequest): BaseResponse<out Any>

    suspend fun getAllMessages(chatId: String): BaseResponse<out Any>

    suspend fun tryDisconnect(socketSession: WebSocketSession): BaseResponse<out Any>
}