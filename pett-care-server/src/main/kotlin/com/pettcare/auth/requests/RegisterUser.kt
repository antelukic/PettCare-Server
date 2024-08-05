package com.pettcare.auth.requests

data class RegisterUser(
    val fullName: String,
    val avatar: String,
    val email: String,
    val password: String,
    val userType: Int,
    val dateOfBirth: String,
    val gender: String
)
