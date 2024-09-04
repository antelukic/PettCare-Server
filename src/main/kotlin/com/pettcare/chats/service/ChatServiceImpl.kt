package com.pettcare.chats.service

import com.pettcare.chats.models.Chat
import com.pettcare.chats.requests.GetChatRequest
import com.pettcare.db.ChatsTable
import com.pettcare.db.DatabaseFactory.dbQuery
import java.util.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class ChatServiceImpl : ChatService {

    override suspend fun getChat(param: GetChatRequest): Chat? {
        var chat: Chat? = null
        var statement: InsertStatement<Number>? = null

        dbQuery {
            chat = ChatsTable.select {
                (ChatsTable.secondUserId eq param.secondUserId) or
                        (ChatsTable.secondUserId eq param.firstUserId) or
                        (ChatsTable.firstUserId eq param.firstUserId) or
                        (ChatsTable.firstUserId eq param.secondUserId)
            }.firstNotNullOfOrNull(::rowToChat)
        }

        return if (chat == null) {
            dbQuery {
                statement = ChatsTable.insert {
                    it[id] = UUID.randomUUID().toString()
                    it[firstUserId] = param.firstUserId
                    it[secondUserId] = param.secondUserId
                }
            }
            rowToChat(statement?.resultedValues?.first())
        } else {
            chat
        }
    }

    override suspend fun getUserChats(userId: String): List<Chat>? {
        var chats: List<Chat>? = null

        dbQuery {
            chats = ChatsTable.select {
                (ChatsTable.secondUserId eq userId) or (ChatsTable.firstUserId eq userId)
            }.mapNotNull(::rowToChat)
        }

        return chats
    }

    private fun rowToChat(row: ResultRow?): Chat? =
        row?.let {
            Chat(
                id = row[ChatsTable.id],
                firstUserId = row[ChatsTable.firstUserId],
                secondUserId = row[ChatsTable.secondUserId]
            )
        }
}