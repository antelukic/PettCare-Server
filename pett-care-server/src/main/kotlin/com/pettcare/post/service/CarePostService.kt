package com.pettcare.post.service

import com.pettcare.post.models.CarePost
import com.pettcare.post.requests.CreateCarePostRequest

interface CarePostService {

    suspend fun createPost(param: CreateCarePostRequest): CarePost?

    suspend fun getPostById(id: String): CarePost?

    suspend fun getPosts(userId: String?, limit: Int?, offset: Long?): List<CarePost>

    suspend fun deletePost(postId: String): Boolean
}