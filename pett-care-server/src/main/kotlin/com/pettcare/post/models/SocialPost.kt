package com.pettcare.post.models

import kotlinx.serialization.SerialName

data class SocialPost(
    val text: String?,
    val id: String,
    val photoUrl: String?,
    val photoId: String?,
    val creatorId: String,
)
