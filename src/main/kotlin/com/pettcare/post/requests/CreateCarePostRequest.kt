package com.pettcare.post.requests

data class CreateCarePostRequest(
    val description: String,
    val lat: Double,
    val lon: Double,
    val address: String,
    val price: String?,
    val photoUrl: String?,
    val photoId: String?,
    val creatorId: String,
)