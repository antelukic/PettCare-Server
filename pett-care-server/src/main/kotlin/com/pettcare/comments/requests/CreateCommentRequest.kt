package com.pettcare.comments.requests

data class CreateCommentRequest(
    val userId: String,
    val postId: String,
    val text: String,
)
