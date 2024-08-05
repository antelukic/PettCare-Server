package com.pettcare.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(config: ApplicationConfig) {
        val driverClassName = config.property("ktor.database.driverClassName").getString()
        val jdbcURL = config.property("ktor.database.jdbcURL").getString()
        val username = config.property("ktor.database.user").getString()
        val password = config.property("ktor.database.password").getString()
        val defaultDatabase = config.property("ktor.database.database").getString()
        val connectionPool = createHikariDataSource(
            url = "$jdbcURL/$defaultDatabase?user=$username&password=$password",
            driver = driverClassName,
        )
        val database = Database.connect(connectionPool)
        transaction(database) {
            SchemaUtils.create(UserTable)
        }
    }

    private fun createHikariDataSource(
        url: String,
        driver: String,
    ) = HikariDataSource(HikariConfig().apply {
        driverClassName = driver
        jdbcUrl = url
        maximumPoolSize = 15
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        maxLifetime = 0
        validate()
    })

    suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO) {
        transaction { block() }
    }
}