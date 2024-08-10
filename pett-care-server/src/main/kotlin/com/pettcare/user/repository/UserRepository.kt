package com.pettcare.user.repository

import com.pettcare.core.BaseResponse
import com.pettcare.user.models.User

interface UserRepository {

    suspend fun getUserById(id: String): BaseResponse<User>

    suspend fun searchUser(query: String, limit: Int?, offset: Long?): BaseResponse<Any>
}