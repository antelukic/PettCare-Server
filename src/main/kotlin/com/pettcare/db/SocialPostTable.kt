package com.pettcare.db

import java.time.LocalDateTime
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object SocialPostTable : Table("social_post_table") {
    val id = char("id", 36)
    val creatorId = char("creator_id", 36).references(UserTable.id)
    val photoUrl = text("photo_url").nullable()
    val photoId = text("photo_id").nullable()
    val numOfLikes = integer("num_of_likes").nullable()
    val text = text("text")
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(id)
}
