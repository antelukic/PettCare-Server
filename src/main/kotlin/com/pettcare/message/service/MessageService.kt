package com.pettcare.message.service

import com.pettcare.message.models.Message
import com.pettcare.message.request.SendMessageRequest

interface MessageService {

    suspend fun getMessages(id: String): List<Message>?

    suspend fun insertMessage(message: SendMessageRequest): Message?
}