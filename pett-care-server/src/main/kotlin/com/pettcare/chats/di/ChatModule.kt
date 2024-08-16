package com.pettcare.chats.di

import com.pettcare.chats.repository.ChatRepository
import com.pettcare.chats.repository.ChatRepositoryImpl
import com.pettcare.chats.service.ChatService
import com.pettcare.chats.service.ChatServiceImpl
import org.koin.dsl.module

val chatModule = module {
    single<ChatService> { ChatServiceImpl() }
    single<ChatRepository> { ChatRepositoryImpl(get()) }
}
