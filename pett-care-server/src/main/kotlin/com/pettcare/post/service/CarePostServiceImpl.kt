package com.pettcare.post.service

import com.pettcare.db.CarePostTable
import com.pettcare.db.DatabaseFactory
import com.pettcare.post.models.CarePost
import com.pettcare.post.requests.CreateCarePostRequest
import java.util.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement

class CarePostServiceImpl : CarePostService {

    override suspend fun createPost(param: CreateCarePostRequest): CarePost? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = CarePostTable.insert {
                it[description] = param.description
                it[photoUrl] = param.photoId.orEmpty()
                it[photoId] = param.photoUrl.orEmpty()
                it[creatorId] = param.creatorId
                it[latitude] = param.lat
                it[longitude] = param.lon
                it[address] = param.address
                it[price] = param.price.toString()
                it[id] = userId()
            }
        }

        return rowToCarePost(statement?.resultedValues?.first())
    }

    override suspend fun deletePost(postId: String): Boolean {
        var numberOfDeletedRows: Int? = null

        DatabaseFactory.dbQuery {
            numberOfDeletedRows = CarePostTable.deleteWhere { id eq postId }
        }

        return numberOfDeletedRows == 1
    }

    override suspend fun getPostById(id: String): CarePost? {
        var carePost: CarePost? = null

        DatabaseFactory.dbQuery {
            carePost = CarePostTable.select {
                CarePostTable.id eq id
            }.firstOrNull().let(::rowToCarePost)
        }

        return carePost
    }

    override suspend fun getPosts(userId: String?, limit: Int?, offset: Long?): List<CarePost> {
        var posts: List<CarePost?>? = null

        DatabaseFactory.dbQuery {
            posts = if (userId != null) {
                CarePostTable.select {
                    (CarePostTable.creatorId eq userId)
                }.limit(
                    n = limit ?: 0,
                    offset = offset ?: 0L
                ).sortedBy { it[CarePostTable.createdAt] }.map(::rowToCarePost)
            } else {
                CarePostTable.selectAll()
                    .limit(
                        n = limit ?: 0,
                        offset = offset ?: 0L
                    ).sortedBy { it[CarePostTable.createdAt] }.map(::rowToCarePost)
            }
        }
        return posts?.filterNotNull() ?: emptyList()
    }

    private fun rowToCarePost(row: ResultRow?): CarePost? =
        row?.let {
            CarePost(
                description = row[CarePostTable.description],
                id = row[CarePostTable.id],
                photoUrl = row[CarePostTable.photoUrl],
                photoId = row[CarePostTable.photoId],
                creatorId = row[CarePostTable.creatorId],
                lat = row[CarePostTable.latitude],
                lon = row[CarePostTable.longitude],
                address = row[CarePostTable.address],
                price = row[CarePostTable.price],
                createdAt = row[CarePostTable.createdAt].toString()
            )
        }

    private fun userId() = UUID.randomUUID().toString()
}