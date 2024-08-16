package com.pettcare.message.request

data class SendMessageRequest(
    val senderId: String,
    val text: String,
    val chatId: String,
)
