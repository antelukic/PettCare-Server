package com.pettcare.post.service

import com.pettcare.db.DatabaseFactory.dbQuery
import com.pettcare.db.SocialPostTable
import com.pettcare.post.models.SocialPost
import com.pettcare.post.requests.CreateSocialPostRequest
import java.util.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.update

class SocialPostServiceImpl: SocialPostService {

    override suspend fun createPost(param: CreateSocialPostRequest): SocialPost? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = SocialPostTable.insert {
                it[text] = param.text.orEmpty()
                it[photoUrl] = param.photoId.orEmpty()
                it[photoUrl] = param.photoUrl.orEmpty()
                it[creatorId] = param.creatorId
                it[id] = userId()
                it[numOfLikes] = 0
            }
        }

        return rowToSocialPost(statement?.resultedValues?.first())
    }

    override suspend fun deletePost(postId: String): Boolean {
        var numberOfDeletedRows: Int? = null

        dbQuery {
            numberOfDeletedRows = SocialPostTable.deleteWhere { id eq postId}
        }

        return numberOfDeletedRows == 1
    }

    override suspend fun getPostById(id: String): SocialPost? {
        var socialPost: SocialPost? = null

        dbQuery {
            socialPost = SocialPostTable.select {
                SocialPostTable.id eq id
            }.firstOrNull().let(::rowToSocialPost)
        }

        return socialPost
    }

    override suspend fun likePost(id: String): Boolean {
        var post: SocialPost?= null
        var numOfRowsChanged: Int? = null
        dbQuery {
            post = SocialPostTable.select {
                SocialPostTable.id eq id
            }.firstOrNull().let(::rowToSocialPost)
        }

        dbQuery {
            numOfRowsChanged = SocialPostTable.update({SocialPostTable.id eq id }){
                it[numOfLikes] = (post?.numOfLikes ?: 0).inc()
            }
        }

        return (numOfRowsChanged ?: 0) > 0
    }

    override suspend fun getPosts(userId: String?, limit: Int?, offset: Long?): List<SocialPost> {
        var posts: List<SocialPost?>? = null

        dbQuery {
            posts = if(userId != null) {
                SocialPostTable.select {
                    (SocialPostTable.creatorId eq userId)
                }.limit(
                    n = limit ?: 0,
                    offset = offset ?: 0L
                ).map(::rowToSocialPost)
            } else {
                SocialPostTable.selectAll()
                    .limit(
                        n = limit ?: 0,
                        offset = offset ?: 0L
                    ).map(::rowToSocialPost)
            }
        }
        return posts?.filterNotNull() ?: emptyList()
    }

    private fun rowToSocialPost(row: ResultRow?): SocialPost? =
        row?.let {
            SocialPost(
                text= row[SocialPostTable.text],
                id = row[SocialPostTable.id],
                photoUrl = row[SocialPostTable.photoUrl],
                photoId = row[SocialPostTable.photoId],
                creatorId = row[SocialPostTable.creatorId],
                numOfLikes = row[SocialPostTable.numOfLikes] ?: 0,
            )
        }

    private fun userId() = UUID.randomUUID().toString()
}