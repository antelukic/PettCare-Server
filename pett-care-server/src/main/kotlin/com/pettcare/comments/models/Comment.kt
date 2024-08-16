package com.pettcare.comments.models

data class Comment(
    val id: String,
    val userId: String,
    val postId: String,
    val text: String,
    val createdAt: String,
)
