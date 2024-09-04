package com.pettcare.comments.repository

import com.pettcare.comments.requests.CreateCommentRequest
import com.pettcare.comments.responses.CreateCommentResponse
import com.pettcare.comments.responses.GetCommentsResponse
import com.pettcare.comments.service.CommentsService
import com.pettcare.core.BaseResponse

class CommentsRepositoryImpl(
    private val commentsService: CommentsService,
): CommentsRepository {

    override suspend fun getCommentByPostId(id: String): BaseResponse<out Any> = kotlin.runCatching {
            val response = commentsService.getCommentsByPostId(id)
            return@runCatching if(response == null){
                BaseResponse.ErrorResponse(data = null, message = "Error occurred while trying to access comments database")
            } else{
                BaseResponse.SuccessResponse(GetCommentsResponse(response))
            }
        }.getOrElse {
            it.printStackTrace()
            BaseResponse.ErrorResponse(it.message.toString())
        }

    override suspend fun addComment(params: CreateCommentRequest): BaseResponse<out Any> =kotlin.runCatching {
        val response = commentsService.addComment(params)
        return@runCatching if(response == null){
            BaseResponse.ErrorResponse(data = null, message = "Error occurred while trying to access comments database")
        } else{
            BaseResponse.SuccessResponse(CreateCommentResponse(true))
        }
    }.getOrElse {
        it.printStackTrace()
        BaseResponse.ErrorResponse(it.message.toString())
    }
}