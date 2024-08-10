package com.pettcare.user.repository

import com.pettcare.core.BaseResponse
import com.pettcare.core.BaseResponse.ErrorResponse
import com.pettcare.core.BaseResponse.SuccessResponse
import com.pettcare.core.PaginatedResponse
import com.pettcare.user.models.User
import com.pettcare.user.service.UserService

class UserRepositoryImpl(private val userService: UserService) : UserRepository {

    override suspend fun getUserById(id: String): BaseResponse<User> = kotlin.runCatching {
        userService.getUserById(id)?.let { SuccessResponse(it) } ?: ErrorResponse(message = "User does not exist")
    }.getOrElse {
        it.printStackTrace()
        return ErrorResponse(
            message = it.message.toString()
        )
    }

    override suspend fun searchUser(
        query: String,
        limit: Int?,
        offset: Long?
    ): BaseResponse<Any> = kotlin.runCatching {
        val calculatedOffset = limit?.times((offset?.toInt() ?: 0))?.toLong()
        val response = userService.searchUser(query, limit, calculatedOffset)
        return if(response == null) {
            ErrorResponse("Error occured while trying to access database")
        } else {
            BaseResponse.SuccessResponse(PaginatedResponse(items = response))
        }
    }.getOrElse {
        it.printStackTrace()
        return ErrorResponse(message = it.message.toString())
    }
}