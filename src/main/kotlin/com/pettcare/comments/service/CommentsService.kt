package com.pettcare.comments.service

import com.pettcare.comments.models.Comment
import com.pettcare.comments.requests.CreateCommentRequest

interface CommentsService {

    suspend fun getCommentsByPostId(id: String): List<Comment>?

    suspend fun addComment(param: CreateCommentRequest): Comment?
}