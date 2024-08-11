package com.pettcare.post.models

data class SocialPost(
    val text: String?,
    val id: String,
    val photoUrl: String?,
    val photoId: String?,
    val creatorId: String,
    val numOfLikes: Int,
)
