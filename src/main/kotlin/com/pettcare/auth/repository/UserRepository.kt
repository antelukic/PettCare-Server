package com.pettcare.auth.repository

import com.pettcare.auth.requests.AuthRequest
import com.pettcare.auth.requests.RegisterUser
import com.pettcare.auth.security.token.TokenConfig
import com.pettcare.core.BaseResponse

interface UserRepository {

    suspend fun registerUser(params: RegisterUser, tokenConfig: TokenConfig): BaseResponse<Any>
    suspend fun loginUser(params: AuthRequest, tokenConfig: TokenConfig): BaseResponse<Any>
}