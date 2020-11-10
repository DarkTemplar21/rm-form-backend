package com.richmeat.data.model

import com.example.richmeat.util.ModuleNames
import com.google.gson.Gson
import com.richmeat.data.model.user.Login
import com.richmeat.data.model.user.Users
import com.richmeat.data.model.user.Users.name
import com.richmeat.data.model.user.Users.password

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.*
import java.util.Date


class DataBaseService {
    private val mDateFormat = "yyyy-MM-dd"
    private val mDateHourFormat = "dd-MM-yyyy-HH-mm"

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }


    object DatabaseFactory {

//            private val appConfig = HoconApplicationConfig(ConfigFactory.load())
//            private val dbUrl = appConfig.property("db.jdbcUrl").getString()
//            private val dbUser = appConfig.property("db.dbUser").getString()
//            private val dbPassword = appConfig.property("db.dbPassword").getString()

        fun init() {
            Database.connect(hikari())
        }

        private fun hikari(): HikariDataSource {
            val config = HikariConfig()
            config.driverClassName = "org.postgresql.Driver"
            config.jdbcUrl = "jdbc:postgresql://127.0.0.1:5432/postgres"
            config.username = "root"
            config.password = "root"
            config.maximumPoolSize = 3
            config.isAutoCommit = false
            config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            config.validate()
            return HikariDataSource(config)


        }


    }

    suspend fun loginRquest(login: Login): Boolean = dbQuery {
        return@dbQuery Users.select { name eq login.name and (password eq login.password) }.count() > 0
    }
}



