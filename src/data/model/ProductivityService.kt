package com.richmeat.data.model

import com.example.richmeat.util.ModuleNames
import com.google.gson.Gson
import com.richmeat.data.model.Productivities.M01
import com.richmeat.data.model.Productivities.M02
import com.richmeat.data.model.Productivities.M03
import com.richmeat.data.model.Productivities.M04
import com.richmeat.data.model.Productivities.M05
import com.richmeat.data.model.Productivities.M1X400
import com.richmeat.data.model.Productivities.M1X800
import com.richmeat.data.model.Productivities.MCEDA
import com.richmeat.data.model.Productivities.date
import com.richmeat.data.model.Productivities.startDate
import com.richmeat.data.model.Productivities.turnNumber
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.*


class ProductivityService {
    private val mDateFormat = "dd-MM-yyyy"
    private val mDateHourFormat = "dd-MM-yyyy-HH-mm"
    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }

    suspend fun getProductivityByDate(mDate: String, turn : Int): List<Productivity> = dbQuery {
        Productivities.select { (date eq mDate and (turnNumber eq turn))}.map {
            toProductivity(it)
        }


    }

    suspend fun getProductivity(): List<Productivity> = dbQuery {
        Productivities.selectAll().map {
            toProductivity(it)
        }.sortedBy { it.date }


    }

    fun insertProductivity(modules: List<Module>) {
        transaction {
            //me quede akiiiiiiiiiiiiiiiiiiiiiiiiiiii
            val dateNow = DateTime.now().toString(mDateFormat)
//            val modules = Gson().fromJson(productivity.modulesFirstTurn, Array<Module>::class.java)
            val dateStartHourNow = DateTime.now().toString(mDateHourFormat)
            val turn = modules[0].turn!!.toInt()
            if (!thisProductivityDateExists(dateNow, turn)) {
                Productivities.insert { productivity ->
                    productivity[id] = UUID.randomUUID()
                    productivity[date] = dateNow
                    productivity[turnNumber] = turn
                    modules.forEach {
                        when(it.moduleName){
                            ModuleNames.MODULE_1X400 -> productivity[M1X400] = it.cant!!.toInt()
                            ModuleNames.MODULE_1X800 -> productivity[M1X800] = it.cant!!.toInt()
                            ModuleNames.MODULE_01 -> productivity[M01] = it.cant!!.toInt()
                            ModuleNames.MODULE_02 -> productivity[M02] = it.cant!!.toInt()
                            ModuleNames.MODULE_03 -> productivity[M03] = it.cant!!.toInt()
                            ModuleNames.MODULE_04 -> productivity[M04] = it.cant!!.toInt()
                            ModuleNames.MODULE_05 -> productivity[M05] = it.cant!!.toInt()
                            ModuleNames.MODULE_CEDA -> productivity[MCEDA] = it.cant!!.toInt()
                        }
                    }


                }
            } else {
                var lastProductivity: Productivity
                Productivities.select { date eq dateNow and (turnNumber eq turn) }.map {
                    lastProductivity = toProductivity(it)
                    updateProductivity(dateNow, modules, lastProductivity, dateStartHourNow)

                }

            }


        }

    }

    private fun updateProductivity(
        dateNow: String,
        modules: List<Module>,
        lastProductivity: Productivity,
        dateStartHourNow: String
    ): Int {
        return Productivities.update({ date eq dateNow and (turnNumber eq lastProductivity.turnNumber) }) { productivity ->
            var modules1X400 = Module(16.00, "", 0, 0)


            modules.forEach {
                when(it.moduleName){
                    ModuleNames.MODULE_1X400 -> productivity[M1X400] = it.cant!!.toInt()
                    ModuleNames.MODULE_1X800 -> productivity[M1X800] = it.cant!!.toInt()
                    ModuleNames.MODULE_01 -> productivity[M01] = it.cant!!.toInt()
                    ModuleNames.MODULE_02 -> productivity[M02] = it.cant!!.toInt()
                    ModuleNames.MODULE_03 -> productivity[M03] = it.cant!!.toInt()
                    ModuleNames.MODULE_04 -> productivity[M04] = it.cant!!.toInt()
                    ModuleNames.MODULE_05 -> productivity[M05] = it.cant!!.toInt()
                    ModuleNames.MODULE_CEDA -> productivity[MCEDA] = it.cant!!.toInt()
                }
            }

            if (modules1X400.cant == 0 && modules.isNotEmpty()) {
                productivity[startDate] = dateStartHourNow
            }

        }
    }
}

private fun thisProductivityDateExists(date: String, turn: Int): Boolean {
    return transaction {
        Productivities.select { Productivities.date eq date and (turnNumber eq turn)}.count()
    } >= 1


}


private fun toProductivity(row: ResultRow): Productivity =
    Productivity(
        startDate = row[startDate],
        date = row[date],
        turnNumber = row[turnNumber],
        M1X400 = row[M1X400],
        M1X800 = row[M1X800],
        M01 = row[M01],
        M02 = row[M02],
        M03 = row[M03],
        M04 = row[M04],
        M05 = row[M05],
        MCEDA = row[MCEDA]

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

