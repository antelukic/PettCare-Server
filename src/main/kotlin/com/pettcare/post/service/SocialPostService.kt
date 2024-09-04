package com.pettcare.post.service

import com.pettcare.post.models.SocialPost
import com.pettcare.post.requests.CreateSocialPostRequest

interface SocialPostService {

    suspend fun createPost(param: CreateSocialPostRequest): SocialPost?

    suspend fun getPostById(id: String): SocialPost?

    suspend fun likePost(id: String): Boolean

    suspend fun getPosts(userId: String?, limit: Int?, offset: Long?): List<SocialPost>

    suspend fun deletePost(postId: String): Boolean
}