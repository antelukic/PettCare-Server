package com.pettcare.auth.service

import com.pettcare.auth.models.User
import com.pettcare.auth.requests.AuthRequest
import com.pettcare.auth.requests.RegisterUser
import com.pettcare.auth.security.hashing.HashingService
import com.pettcare.auth.security.hashing.SaltedHash
import com.pettcare.db.DatabaseFactory.dbQuery
import com.pettcare.db.UserTable
import java.util.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

private const val EMPTY = ""

class UserServiceImpl(
    private val hashingService: HashingService
) : UserService {

    override suspend fun registerUser(param: RegisterUser): User? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            val saltedHash = hashingService.generateSaltedHash(param.password)

            statement = UserTable.insert {
                it[id] = userId()
                it[email] = param.email
                it[password] = saltedHash.hash
                it[salt] = saltedHash.salt
                it[fullName] = param.fullName
                it[avatar] = param.avatar
                it[notificationToken] = EMPTY
                it[imageId] = param.imageId
                it[dateOfBirth] = param.dateOfBirth
                it[gender] = param.gender.first().toString()
            }
        }
        return rowToUser(statement?.resultedValues?.first())
    }

    override suspend fun loginUser(params: AuthRequest): User? {
        val user = findUserByEmail(params.email)

        return if (user != null) {

            val isValidPassword = hashingService.verify(
                value = params.password,
                saltedHash = SaltedHash(
                    hash = user.password,
                    salt = user.salt
                )
            )
            if (!isValidPassword) {
                null
            } else user
        } else
            null
    }

    override suspend fun findUserByEmail(email: String): User? {
        val user = dbQuery {
            UserTable.select { UserTable.email.eq(email) }.map(::rowToUser).singleOrNull()
        }
        return user
    }

    override suspend fun findUserById(id: String): User? {
        val user = dbQuery {
            UserTable.select { UserTable.id.eq(id) }.map(::rowToUser).singleOrNull()
        }
        return user
    }

    private fun rowToUser(row: ResultRow?): User? {
        return if (row == null) null
        else User(
            id = row[UserTable.id],
            fullName = row[UserTable.fullName],
            avatar = row[UserTable.avatar],
            email = row[UserTable.email],
            createdAt = row[UserTable.createdAt].toString(),
            password = row[UserTable.password].toString(),
            salt = row[UserTable.salt].toString(),
            notificationToken = row[UserTable.notificationToken],
            imageId = row[UserTable.imageId],
            dateOfBirth = row[UserTable.dateOfBirth],
            gender = row[UserTable.gender]
        )
    }

    private fun userId() = UUID.randomUUID().toString()
}
