package com.richmeat.data.model.user

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*



object Users: Table() {
    val role: Column<String> = text("email")
    val userName: Column<String> = text("user_name")
    val password: Column<String> = text("password")
}




