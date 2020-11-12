package com.richmeat.data.model

import com.richmeat.data.model.form.Form
import com.richmeat.data.model.form.Forms
import com.richmeat.data.model.user.Login
import com.richmeat.data.model.user.Users
import com.richmeat.data.model.user.Users.name
import com.richmeat.data.model.user.Users.password
import com.richmeat.data.model.user.Users.userName

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


    suspend fun loginRequest(login: Login): Boolean = dbQuery {
        return@dbQuery Users.select { userName eq login.userName and (password eq login.password) }.count() > 0
    }

}



