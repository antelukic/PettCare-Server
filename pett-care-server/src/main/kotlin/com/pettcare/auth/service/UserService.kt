package com.pettcare.auth.service

import com.pettcare.auth.models.User
import com.pettcare.auth.requests.AuthRequest
import com.pettcare.auth.requests.RegisterUser

interface UserService {

    suspend fun registerUser(param: RegisterUser): User?

    suspend fun loginUser(params: AuthRequest): User?

    suspend fun findUserByEmail(email: String): User?

    suspend fun findUserById(id: String): User?
}
