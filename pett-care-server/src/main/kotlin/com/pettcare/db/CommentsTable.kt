package com.pettcare.db

import org.jetbrains.exposed.sql.Table

object CommentsTable : Table("comments_table") {
    val id = char("id", 36)
    val postId = char("post_id", 36)
    val creatorId = char("creator_id", 36).references(UserTable.id)
    val text = text("text")
    override val primaryKey = PrimaryKey(id)
}