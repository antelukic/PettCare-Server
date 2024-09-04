package com.pettcare.db

import java.time.LocalDateTime
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object CommentsTable : Table("comments_table") {
    val id = char("id", 36)
    val postId = char("post_id", 36)
    val creatorId = char("creator_id", 36).references(UserTable.id)
    val text = text("text")
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(id)
}