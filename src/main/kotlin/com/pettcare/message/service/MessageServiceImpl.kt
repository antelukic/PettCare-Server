package com.pettcare.message.service

import com.pettcare.db.DatabaseFactory.dbQuery
import com.pettcare.db.MessagesTable
import com.pettcare.message.models.Message
import com.pettcare.message.request.SendMessageRequest
import java.util.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class MessageServiceImpl : MessageService {

    override suspend fun getMessages(id: String): List<Message>? {
        var messages: List<Message>? = null

        dbQuery {
            messages = MessagesTable.select {
                MessagesTable.chatId eq id
            }.mapNotNull(::rowToMessage)
        }

        return messages
    }

    override suspend fun insertMessage(message: SendMessageRequest): Message? {
        var statement: InsertStatement<Number>? = null

        dbQuery {
            statement = MessagesTable.insert {
                it[text] = message.text
                it[id] = UUID.randomUUID().toString()
                it[senderId] = message.senderId
                it[chatId] = message.chatId
            }
        }

        return rowToMessage(statement?.resultedValues?.first())
    }

    private fun rowToMessage(row: ResultRow?): Message? =
        row?.let {
            Message(
                text = row[MessagesTable.text],
                id = row[MessagesTable.id],
                chatId = row[MessagesTable.chatId],
                senderId = row[MessagesTable.senderId],
                dateTime = row[MessagesTable.createdAt].toString()
            )
        }
}