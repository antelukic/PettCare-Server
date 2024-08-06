package com.pettcare.post.repository

import com.pettcare.core.BaseResponse
import com.pettcare.core.PaginatedResponse
import com.pettcare.post.requests.CreateSocialPostRequest
import com.pettcare.post.responses.DeletePostResponse
import com.pettcare.post.responses.SocialPostResponse
import com.pettcare.post.service.SocialPostService

class SocialPostRepositoryImpl(
    private val socialPostService: SocialPostService,
) : SocialPostRepository {

    override suspend fun createPost(param: CreateSocialPostRequest): BaseResponse<Any> = kotlin.runCatching {
        val result = socialPostService.createPost(param)

        return if (result != null)
            BaseResponse.SuccessResponse(
                data = SocialPostResponse(result)
            )
        else
            BaseResponse.ErrorResponse(
                data = SocialPostResponse(response = null),
                message = "Error saving the event"
            )

    }.getOrElse {
        it.printStackTrace()
        return BaseResponse.ErrorResponse(
            data = SocialPostResponse(response = null),
            message = it.message.toString()
        )
    }

    override suspend fun deletePost(id: String): BaseResponse<Any> = kotlin.runCatching {
        val result = socialPostService.deletePost(id)

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
        val socialPost = socialPostService.getPostById(id)

        return BaseResponse.SuccessResponse(
            data = SocialPostResponse(socialPost)
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
            val request = socialPostService.getPosts(userId, limit, calculatedOffset)
            return BaseResponse.SuccessResponse(PaginatedResponse(items = request))
        }.getOrElse {
            it.printStackTrace()
            return BaseResponse.ErrorResponse(message = it.message.toString())
        }
}