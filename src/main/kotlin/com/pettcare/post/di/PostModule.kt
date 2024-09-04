package com.pettcare.post.di

import com.pettcare.post.repository.CarePostRepository
import com.pettcare.post.repository.CarePostRepositoryImpl
import com.pettcare.post.repository.SocialPostRepository
import com.pettcare.post.repository.SocialPostRepositoryImpl
import com.pettcare.post.service.CarePostService
import com.pettcare.post.service.CarePostServiceImpl
import com.pettcare.post.service.SocialPostService
import com.pettcare.post.service.SocialPostServiceImpl
import org.koin.dsl.module

val postModule = module {
    single<SocialPostService> { SocialPostServiceImpl() }
    single<SocialPostRepository> { SocialPostRepositoryImpl(get()) }

    single<CarePostService> { CarePostServiceImpl() }
    single<CarePostRepository> { CarePostRepositoryImpl(get()) }
}