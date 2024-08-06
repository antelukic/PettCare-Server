package com.pettcare.db

import org.jetbrains.exposed.sql.Table

object CarePostTable : Table("care_post_table") {
    val id = char("id", 36)
    val creatorId = char("creator_id", 36).references(UserTable.id)
    val photoUrl = text("photo_url")
    val photoId = text("photo_id")
    val latitude = double("latitude")
    val longitude = double("longitude")
    val address = text("text")
    val price = text("price")
    val description = text("description")
    override val primaryKey = PrimaryKey(id)
}
