package com.pettcare.comments.service

import com.pettcare.comments.models.Comment
import com.pettcare.comments.requests.CreateCommentRequest
import com.pettcare.db.CommentsTable
import com.pettcare.db.DatabaseFactory
import com.pettcare.db.UserTable
import java.util.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class CommentsServiceImpl: CommentsService {

    override suspend fun getCommentsByPostId(id: String): List<Comment>? {
        var comments: List<Comment>? = null

        DatabaseFactory.dbQuery {
            comments = CommentsTable.select {
                CommentsTable.postId eq id
            }.sortedBy { it[UserTable.createdAt] }.mapNotNull(::rowToComment)
        }

        return comments
    }

    override suspend fun addComment(param: CreateCommentRequest): Comment? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = CommentsTable.insert {
                it[creatorId] = param.userId
                it[postId] = param.postId
                it[text] = param.text
                it[id] = UUID.randomUUID().toString()
            }
        }

        return rowToComment(statement?.resultedValues?.first())
    }

    private fun rowToComment(row: ResultRow?): Comment? =
        row?.let {
            Comment(
                id = row[CommentsTable.id],
                userId = row[CommentsTable.creatorId],
                postId = row[CommentsTable.postId],
                text = row[CommentsTable.text],
                createdAt = row[CommentsTable.createdAt].toString(),
            )
        }
}