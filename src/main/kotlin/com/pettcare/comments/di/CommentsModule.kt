package com.pettcare.comments.di

import com.pettcare.comments.repository.CommentsRepository
import com.pettcare.comments.repository.CommentsRepositoryImpl
import com.pettcare.comments.service.CommentsService
import com.pettcare.comments.service.CommentsServiceImpl
import org.koin.dsl.module

val commentsModule = module {
    single<CommentsService> { CommentsServiceImpl() }
    single<CommentsRepository> { CommentsRepositoryImpl(get()) }
}