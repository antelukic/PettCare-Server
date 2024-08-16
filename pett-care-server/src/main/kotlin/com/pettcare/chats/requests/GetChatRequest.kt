package com.pettcare.chats.requests

data class GetChatRequest(
    val firstUserId: String,
    val secondUserId: String,
)
