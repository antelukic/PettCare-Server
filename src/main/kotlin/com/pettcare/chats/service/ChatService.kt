package com.pettcare.chats.service

import com.pettcare.chats.models.Chat
import com.pettcare.chats.requests.GetChatRequest

interface ChatService {

    suspend fun getUserChats(userId: String): List<Chat>?

    suspend fun getChat(param: GetChatRequest): Chat?
}