package com.pettcare.comments.repository

import com.pettcare.comments.requests.CreateCommentRequest
import com.pettcare.core.BaseResponse

interface CommentsRepository {

    suspend fun getCommentByPostId(id: String): BaseResponse<out Any>

    suspend fun addComment(params: CreateCommentRequest): BaseResponse<out Any>
}