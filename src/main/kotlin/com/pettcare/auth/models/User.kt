package com.pettcare.auth.models

data class User(
    val id: String,
    val fullName: String,
    val avatar: String,
    val email: String,
    var authToken: String? = null,
    val createdAt: String,
    val password: String,
    val salt: String,
    val imageId: String,
    val dateOfBirth: String,
    val gender: String,
    val notificationToken: String
)
