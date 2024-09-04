package com.pettcare.plugins

import com.pettcare.routes.authRoutes
import com.pettcare.routes.chatRoutes
import com.pettcare.routes.commentRoutes
import com.pettcare.routes.messageRoutes
import com.pettcare.routes.postRoutes
import com.pettcare.routes.userRoutes
import io.ktor.server.application.*

fun Application.configureRouting() {
    authRoutes()
    postRoutes()
    userRoutes()
    commentRoutes()
    chatRoutes()
    messageRoutes()
}
