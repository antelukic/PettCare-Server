package com.pettcare.post.models

data class CarePost(
    val description: String,
    val lat: Double,
    val lon: Double,
    val address: String,
    val id: String,
    val price: String?,
    val photoUrl: String?,
    val photoId: String?,
    val creatorId: String,
    val createdAt: String,
)