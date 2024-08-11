package com.pettcare.routes

import com.pettcare.comments.repository.CommentsRepository
import com.pettcare.comments.requests.CreateCommentRequest
import com.pettcare.core.BaseResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Route.createComment(commentsRepository: CommentsRepository) {
    authenticate {
        post("/comments/add") {
            val request = call.receiveNullable<CreateCommentRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val result = commentsRepository.addComment(params = request)
            call.respond(result.statusCode, result)
        }
    }
}

fun Route.getCommentsForPost(commentsRepository: CommentsRepository) {
    authenticate {
        get("/comments") {
            val postId = call.request.queryParameters["id"]
            if(postId.isNullOrBlank()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    BaseResponse.ErrorResponse(
                        data = null,
                        message = "id cannot be null"
                    )
                )
                return@get
            }
            val result = commentsRepository.getCommentByPostId(postId)
            call.respond(result.statusCode, result)
        }
    }
}

fun Application.commentRoutes() {
    val commentsRepository by inject<CommentsRepository>()
    routing {
        createComment(commentsRepository)
        getCommentsForPost(commentsRepository)
    }
}
