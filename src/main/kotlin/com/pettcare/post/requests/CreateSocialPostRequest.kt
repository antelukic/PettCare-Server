package com.pettcare.post.requests

data class CreateSocialPostRequest(
    val text: String?,
    val photoUrl: String?,
    val photoId: String?,
    val creatorId: String,
)