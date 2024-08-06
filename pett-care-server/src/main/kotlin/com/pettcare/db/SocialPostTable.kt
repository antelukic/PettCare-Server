package com.pettcare.db

import org.jetbrains.exposed.sql.Table

object SocialPostTable : Table("social_post_table") {
    val id = char("id", 36)
    val creatorId = char("creator_id", 36).references(UserTable.id)
    val photoUrl = text("photo_url").nullable()
    val photoId = text("photo_id").nullable()
    val text = text("text")
    override val primaryKey = PrimaryKey(id)
}
