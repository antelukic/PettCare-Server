package com.pettcare.chats.repository

import com.pettcare.chats.requests.GetChatRequest
import com.pettcare.chats.service.ChatService
import com.pettcare.core.BaseResponse

class ChatRepositoryImpl(private val service: ChatService): ChatRepository {

    override suspend fun getAllUserChats(userId: String): BaseResponse<out Any> =
        kotlin.runCatching {
            val chats = service.getUserChats(userId)
            if(chats != null) {
                BaseResponse.SuccessResponse(chats)
            } else {
                BaseResponse.ErrorResponse(message = "Unable to reach the database")
            }
        }.getOrElse {
            it.printStackTrace()
            BaseResponse.ErrorResponse(message = it.message.toString())
        }

    override suspend fun getChat(param: GetChatRequest): BaseResponse<out Any> =
        kotlin.runCatching {
            val chat = service.getChat(param)
            if(chat != null) {
                BaseResponse.SuccessResponse(chat)
            } else {
                BaseResponse.ErrorResponse("Unable to reach the database")
            }
        }.getOrElse {
            it.printStackTrace()
            BaseResponse.ErrorResponse(it.message.toString())
        }
}