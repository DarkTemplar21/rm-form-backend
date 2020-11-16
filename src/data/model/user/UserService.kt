package com.richmeat.data.model.user


import com.richmeat.data.model.user.Users.email
import com.richmeat.data.model.user.Users.name
import com.richmeat.data.model.user.Users.password
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


class UserService {
    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }

    suspend fun getAllUsers(): List<User> = dbQuery {
        var users = mutableListOf<User>()
        Users.selectAll().map {
            toUser(it)

        }
    }
    object DatabaseFactory {
        fun init() {
            Database.connect(hikari())
        }

        private fun hikari(): HikariDataSource {
            //ok for heroku
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

             //local database
//            val config = HikariConfig()
//            config.driverClassName = "org.postgresql.Driver"
//            config.jdbcUrl = "jdbc:postgresql://127.0.0.1:5432/RMForm"
//            config.username = "postgres"
//            config.password = "postgres"
//            config.maximumPoolSize = 3
//            config.isAutoCommit = false
//            config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
//            config.validate()
//            return HikariDataSource(config)
        }



        fun insertUser(user: UserDTO) {
//        transaction {
//            if (thisDniExists(user.dni)) {
//                //me quede akiiiiiiiiiiiiiiiiiiiiiiiiiiii
//                Users.slice(exitsCount).select(dni eq user.dni)
//                Users.update({ dni eq user.dni }) {
//                    it[firstName] = user.firstName
//                    it[lastName] = user.lastName
////                    it[exitsCount] = integer(exitsCount)+1
//
//                }
//            } else {
//                Users.insert {
//                    it[id] = UUID.randomUUID()
//                    it[firstName] = user.firstName
//                    it[lastName] = user.lastName
//                    it[dni] = user.dni
//                    it[exitsCount] = "0"
//                }
//
//            }
//
//        }
    }



}


private fun toUser(row: ResultRow): User =
    User(
        name = row[name],
        email = row[email],
        password = row[password]
    )




}
