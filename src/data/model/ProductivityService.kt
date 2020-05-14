package com.richmeat.data.model

import com.richmeat.data.model.Productivities.boxCant
import com.richmeat.data.model.Productivities.date
import com.richmeat.data.model.Productivities.id
import com.richmeat.data.model.Productivities.key
import com.richmeat.data.model.user.Users.dni
import com.richmeat.data.model.user.Users.exitsCount
import com.richmeat.data.model.user.Users.id
import com.richmeat.data.model.user.UserDTO
import com.richmeat.data.model.user.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.css.keygen
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*


class ProductivityService {
    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }

    suspend fun getProductivity(): List<Productivity> = dbQuery {
        Productivities.selectAll().map {
            toProductivity(it)
        }


    }

    fun insertProductivity(productivity: Productivity) {
        transaction {
                //me quede akiiiiiiiiiiiiiiiiiiiiiiiiiiii
            if (thisProductivityIdExists(productivity.id)){
                Productivities.insert {
                    it[id] = 1
                    it[key] = 4455
                    it[boxCant] = productivity.boxCant
                }
            }else{
                Productivities.update ({Productivities.id eq productivity.id}){
                    it[id] = productivity.id
                    it[key] = productivity.key
                    it[boxCant] = productivity.boxCant
                }
            }


            }

        }
    }
private fun thisProductivityIdExists(id: Int): Boolean {
    return transaction {
        Productivities.selectAll().count()
    } == 1


}





    private fun toProductivity(row: ResultRow): Productivity =
        Productivity(
            id = row[Productivities.id],
            key = row[key],
            boxCant = row[boxCant]

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

