package com.pettcare.post.repository

import com.pettcare.core.BaseResponse
import com.pettcare.post.requests.CreateSocialPostRequest

interface SocialPostRepository {

    suspend fun createPost(param: CreateSocialPostRequest): BaseResponse<Any>

    suspend fun getPostById(id: String): BaseResponse<Any>

    suspend fun getPosts(userId: String?, limit: Int?, offset: Long?): BaseResponse<Any>

    suspend fun deletePost(id: String): BaseResponse<Any>
}