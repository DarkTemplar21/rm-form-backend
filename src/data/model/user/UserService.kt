package com.richmeat.data.model.user

import com.richmeat.data.model.user.Users.dni
import com.richmeat.data.model.user.Users.exitsCount
import com.richmeat.data.model.user.Users.firstName
import com.richmeat.data.model.user.Users.id
import com.richmeat.data.model.user.Users.lastName
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*


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

    fun insertUser(user: UserDTO) {
        transaction {
            if (thisDniExists(user.dni)) {
                //me quede akiiiiiiiiiiiiiiiiiiiiiiiiiiii
                Users.slice(exitsCount).select(dni eq user.dni)
                Users.update({ dni eq user.dni }) {
                    it[firstName] = user.firstName
                    it[lastName] = user.lastName
//                    it[exitsCount] = integer(exitsCount)+1

                }
            } else {
                Users.insert {
                    it[id] = UUID.randomUUID()
                    it[firstName] = user.firstName
                    it[lastName] = user.lastName
                    it[dni] = user.dni
                    it[exitsCount] = "0"
                }

            }

        }
    }

    private fun thisDniExists(dni: Long): Boolean {
        return transaction {
            Users.select(Users.dni eq dni).count()
        } == 1


    }

    fun insertUsers(users: List<UserDTO>) {
        transaction {
            for (user in users) {
                if (thisDniExists(user.dni)) {
                    Users.update({ dni eq user.dni }) {
                        it[id] = UUID.randomUUID()
                        it[firstName] = user.firstName
                        it[lastName] = user.lastName
                        it[dni] = user.dni
                        it[exitsCount] = "user.exitsCount + 1"
                    }
                } else {
                    Users.insert {
                        it[id] = UUID.randomUUID()
                        it[firstName] = user.firstName
                        it[lastName] = user.lastName
                        it[dni] = user.dni
                        it[exitsCount] = "0"
                    }
                }

            }

        }
    }


    private fun toUser(row: ResultRow): User =
        User(
            id = row[id],
            firstName = row[firstName],
            lastName = row[lastName],
            dni = row[dni],
            exitsCount = row[exitsCount]
        )

    object DatabaseFactory {

//            private val appConfig = HoconApplicationConfig(ConfigFactory.load())
//            private val dbUrl = appConfig.property("db.jdbcUrl").getString()
//            private val dbUser = appConfig.property("db.dbUser").getString()
//            private val dbPassword = appConfig.property("db.dbPassword").getString()

        fun init() {
            Database.connect(hikari())
        }

        private fun hikari(): HikariDataSource {
            //ok for heroku
            val config = HikariConfig()
            config.driverClassName = "org.postgresql.Driver"
            config.jdbcUrl = "jdbc:postgresql://ec2-52-44-55-63.compute-1.amazonaws.com:5432/da2bvcvg2pb9gl"
            config.username = "xoxokpbwxaqxew"
            config.password = "2c4d354439dd68be1c252495556e94a967cd07e0ce625a854a6b9bf60ce46610"
            config.maximumPoolSize = 3
            config.isAutoCommit = false
            config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            config.validate()
            return HikariDataSource(config)

//            val config = HikariConfig()
//            config.driverClassName = "org.postgresql.Driver"
//            config.jdbcUrl = "jdbc:postgresql://127.0.0.1:5432/postgres"
//            config.username = "root"
//            config.password = "root"
//            config.maximumPoolSize = 3
//            config.isAutoCommit = false
//            config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
//            config.validate()
//            return HikariDataSource(config)
        }


    }
}
