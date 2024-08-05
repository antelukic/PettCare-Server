package com.pettcare.db


import java.time.LocalDateTime
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object UserTable : Table("users") {
    val id = char("id", 36)
    val fullName = varchar("full_name", 256)
    val avatar = text("avatar")
    val email = varchar("email", 256)
    val password = text("password")
    val salt = text("salt")
    val userType = integer("user_type")
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    val notificationToken = varchar("notification_token", 256)
    val dateOfBirth = varchar("date_of_birth", 16)
    val gender = char("gender", 1)
    override val primaryKey = PrimaryKey(id)
}