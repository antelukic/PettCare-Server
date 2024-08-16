package com.pettcare.db

import java.time.LocalDateTime
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object MessagesTable: Table("messages") {
    val id = char("id", 36)
    val senderId = char("sender_id", 36).references(UserTable.id)
    val text = text("text")
    val chatId = char("chat_id", 36).references(ChatsTable.id)
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(UserTable.id)
}