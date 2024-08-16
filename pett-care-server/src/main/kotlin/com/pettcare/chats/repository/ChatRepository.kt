package com.pettcare.chats.repository

import com.pettcare.chats.requests.GetChatRequest
import com.pettcare.core.BaseResponse

interface ChatRepository {

    suspend fun getChat(param: GetChatRequest): BaseResponse<out Any>

    suspend fun getAllUserChats(userId: String): BaseResponse<out Any>
}