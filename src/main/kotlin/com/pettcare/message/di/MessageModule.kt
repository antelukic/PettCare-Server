package com.pettcare.message.di

import com.pettcare.message.repository.MessageRepository
import com.pettcare.message.repository.MessageRepositoryImpl
import com.pettcare.message.service.MessageService
import com.pettcare.message.service.MessageServiceImpl
import org.koin.dsl.module

val messageModule = module {
    single<MessageService> { MessageServiceImpl() }
    single<MessageRepository> { MessageRepositoryImpl(get()) }
}
