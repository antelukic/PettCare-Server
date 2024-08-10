package com.pettcare.user.service

import com.pettcare.user.models.User

interface UserService {

    suspend fun getUserById(id: String): User?

    suspend fun searchUser(query: String, limit: Int?, offset: Long?): List<User>?
}