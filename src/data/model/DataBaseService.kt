package com.richmeat.data.model

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
            config.jdbcUrl = "jdbc:postgresql://ec2-3-211-176-230.compute-1.amazonaws.com:5432/dd3chvgjm1lt03"
            config.username = "zqsnifaigoxejw"
            config.password = "5854e1897c320fd18cd98c612a55cf309a5531b01bbf5d72f42a8df1f8f84867"
            config.maximumPoolSize = 3
            config.isAutoCommit = false
            config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            config.validate()
            return HikariDataSource(config)


        }


    }

    suspend fun loginRquest(login: Login): Boolean = dbQuery {
        return@dbQuery Users.select { name eq login.userName and (password eq login.password) }.count() > 0
    }
}



