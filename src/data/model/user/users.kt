package com.richmeat.data.model.user

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*



object Users: Table() {
    val name: Column<String> = text("name")
    val email: Column<String> = text("email")
    val password: Column<String> = text("password")
}




