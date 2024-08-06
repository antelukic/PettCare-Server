package com.pettcare.routes

import com.pettcare.auth.repository.UserRepository
import com.pettcare.auth.security.token.TokenConfig
import com.pettcare.core.BaseResponse
import com.pettcare.post.repository.CarePostRepository
import com.pettcare.post.repository.SocialPostRepository
import com.pettcare.post.requests.CreateCarePostRequest
import com.pettcare.post.requests.CreateSocialPostRequest
import com.pettcare.post.requests.DeletePostRequest
import io.ktor.server.application.Application
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.createSocialPost(repository: SocialPostRepository) {
    authenticate {
        post("/socialPost/add") {
            val params = call.receive<CreateSocialPostRequest>()
            val result = repository.createPost(params)
            call.respond(result.statusCode, result)
        }
    }
}

fun Route.deletePost(repository: SocialPostRepository) {
    authenticate {
        post ("/socialPost/delete") {
            val params = call.receive<DeletePostRequest>()
            val result = repository.deletePost(params.id)
            call.respond(result.statusCode, result)
        }
    }
}

fun Route.getPosts(repository: SocialPostRepository) {
    authenticate {
        get ("/socialPosts") {
            val pageSize = call.request.queryParameters["pageSize"]
            val pageNumber = call.request.queryParameters["pageNumber"]
            val userId = call.request.queryParameters["userId"]
            val result = repository.getPosts(userId, pageSize?.toIntOrNull(), pageNumber?.toLongOrNull())
            call.respond(result.statusCode, result)
        }
    }
}

fun Route.getPostById(repository: SocialPostRepository) {
    authenticate {
        get ("/socialPost") {
            val id = call.request.queryParameters["id"]
            if(id.isNullOrBlank()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    BaseResponse.ErrorResponse(
                        data = null,
                        message = "id cannot be null"
                    )
                )
                return@get
            }
            val result = repository.getPostById(id)
            call.respond(result.statusCode, result)
        }
    }
}

fun Route.createCarePost(repository: CarePostRepository) {
    authenticate {
        post("/carePost/add") {
            val params = call.receive<CreateCarePostRequest>()
            val result = repository.createPost(params)
            call.respond(result.statusCode, result)
        }
    }
}

fun Route.deleteCarePost(repository: CarePostRepository) {
    authenticate {
        post ("/carepost/delete") {
            val params = call.receive<DeletePostRequest>()
            val result = repository.deletePost(params.id)
            call.respond(result.statusCode, result)
        }
    }
}

fun Route.getCarePosts(repository: CarePostRepository) {
    authenticate {
        get ("/carePosts") {
            val pageSize = call.request.queryParameters["pageSize"]
            val pageNumber = call.request.queryParameters["pageNumber"]
            val userId = call.request.queryParameters["userId"]
            val result = repository.getPosts(userId, pageSize?.toIntOrNull(), pageNumber?.toLongOrNull())
            call.respond(result.statusCode, result)
        }
    }
}

fun Route.getCarePostById(repository: CarePostRepository) {
    authenticate {
        get ("/carePost") {
            val id = call.request.queryParameters["id"]
            if(id.isNullOrBlank()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    BaseResponse.ErrorResponse(
                        data = null,
                        message = "id cannot be null"
                    )
                )
                return@get
            }
            val result = repository.getPostById(id)
            call.respond(result.statusCode, result)
        }
    }
}


fun Application.postRoutes() {
    val socialPostRepository by inject<SocialPostRepository>()
    val carePostRepository by inject<CarePostRepository>()
    routing {
        deletePost(socialPostRepository)
        getPosts(socialPostRepository)
        createSocialPost(socialPostRepository)
        getPostById(socialPostRepository)

        deleteCarePost(carePostRepository)
        getCarePostById(carePostRepository)
        getCarePosts(carePostRepository)
        createCarePost(carePostRepository)
    }
}
