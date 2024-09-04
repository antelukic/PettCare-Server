package com.pettcare.user.models

data class User(
    val id: String,
    val name: String,
    val email: String,
    val gender: String,
    val dateOfBirth: String,
    val photoUrl: String,
    val imageId: String,
)
