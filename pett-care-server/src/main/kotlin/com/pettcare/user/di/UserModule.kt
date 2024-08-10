package com.pettcare.user.di

import com.pettcare.user.repository.UserRepository
import com.pettcare.user.repository.UserRepositoryImpl
import com.pettcare.user.service.UserService
import com.pettcare.user.service.UserServiceImpl
import org.koin.dsl.module

val userModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<UserService> { UserServiceImpl() }
}