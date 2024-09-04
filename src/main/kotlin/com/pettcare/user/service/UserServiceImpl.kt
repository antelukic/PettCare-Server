package com.pettcare.user.service

import com.pettcare.db.DatabaseFactory
import com.pettcare.db.UserTable
import com.pettcare.user.models.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.trim

class UserServiceImpl: UserService {

    override suspend fun getUserById(id: String): User? {
        var user: User? = null

        DatabaseFactory.dbQuery {
            user = UserTable.select {
                UserTable.id eq id
            }.firstOrNull().let(::rowToUser)
        }

        return user
    }

    override suspend fun searchUser(query: String, limit: Int?, offset: Long?): List<User>? {
        var users: List<User>? = null

        DatabaseFactory.dbQuery {
            users = UserTable.select {
                UserTable.fullName.lowerCase().trim() like "%${query.lowercase().trim()}%"
            }.limit(
                n = limit ?: 0,
                offset = offset ?: 0L
            ).mapNotNull(::rowToUser)
        }

        return users
    }

    private fun rowToUser(row: ResultRow?): User? =
        row?.let {
            User(
                id = row[UserTable.id],
                name = row[UserTable.fullName],
                email = row[UserTable.email],
                gender = row[UserTable.gender],
                dateOfBirth = row[UserTable.dateOfBirth],
                photoUrl = row[UserTable.avatar],
                imageId = row[UserTable.imageId]
            )
        }
}