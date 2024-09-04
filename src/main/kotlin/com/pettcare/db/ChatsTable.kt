package com.pettcare.db

import org.jetbrains.exposed.sql.Table

object ChatsTable: Table("chats") {
    val id = char("id", 36)
    val firstUserId = char("first_user_id", 36).references(UserTable.id)
    val secondUserId = char("second_user_id", 36).references(UserTable.id)
    override val primaryKey = PrimaryKey(id)
}