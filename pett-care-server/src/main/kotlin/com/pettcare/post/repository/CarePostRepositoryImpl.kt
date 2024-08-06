package com.pettcare.post.repository

import com.pettcare.core.BaseResponse
import com.pettcare.core.PaginatedResponse
import com.pettcare.post.requests.CreateCarePostRequest
import com.pettcare.post.responses.CarePostResponse
import com.pettcare.post.responses.DeletePostResponse
import com.pettcare.post.service.CarePostService

class CarePostRepositoryImpl(
    private val carePostService: CarePostService,
) : CarePostRepository {

    override suspend fun createPost(param: CreateCarePostRequest): BaseResponse<Any> = kotlin.runCatching {
        val result = carePostService.createPost(param)

        return if (result != null)
            BaseResponse.SuccessResponse(
                data = CarePostResponse(result)
            )
        else
            BaseResponse.ErrorResponse(
                data = CarePostResponse(response = null),
                message = "Error saving the event"
            )

    }.getOrElse {
        it.printStackTrace()
        return BaseResponse.ErrorResponse(
            data = CarePostResponse(response = null),
            message = it.message.toString()
        )
    }

    override suspend fun deletePost(id: String): BaseResponse<Any> = kotlin.runCatching {
        val result = carePostService.deletePost(id)

        return if (result)
            BaseResponse.SuccessResponse(data = DeletePostResponse(true))
        else BaseResponse.ErrorResponse(
            data = DeletePostResponse(false),
            message = "There was no event in the database"
        )
    }.getOrElse {
        it.printStackTrace()
        BaseResponse.ErrorResponse(
            data = DeletePostResponse(false),
            message = it.message.toString()
        )
    }

    override suspend fun getPostById(id: String): BaseResponse<Any> = runCatching {
        val socialPost = carePostService.getPostById(id)

        return BaseResponse.SuccessResponse(
            data = CarePostResponse(socialPost)
        )
    }.getOrElse {
        it.printStackTrace()
        return BaseResponse.ErrorResponse(
            message = it.message.toString()
        )
    }

    override suspend fun getPosts(userId: String?, limit: Int?, offset: Long?): BaseResponse<Any> =
        kotlin.runCatching {
            val calculatedOffset = limit?.times((offset?.toInt() ?: 0))?.toLong()
            val request = carePostService.getPosts(userId, limit, calculatedOffset)
            return BaseResponse.SuccessResponse(PaginatedResponse(items = request))
        }.getOrElse {
            it.printStackTrace()
            return BaseResponse.ErrorResponse(message = it.message.toString())
        }
}