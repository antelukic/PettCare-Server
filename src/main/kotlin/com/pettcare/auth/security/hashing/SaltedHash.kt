package com.pettcare.auth.security.hashing

data class SaltedHash(
    val hash: String,
    val salt: String
)
