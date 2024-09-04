package com.pettcare.message.models

data class Message(
    val id: String,
    val chatId: String,
    val senderId: String,
    val text: String,
    val dateTime: String,
)
