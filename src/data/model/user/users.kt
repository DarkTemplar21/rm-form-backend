package com.richmeat.data.model.user

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*



object Users: Table() {
    val id: Column<UUID> = uuid("id").autoIncrement().primaryKey()
    val firstName: Column<String> = text("first_name")
    val lastName: Column<String> = text("last_name")
    val dni: Column<Long> = long("dni")
    val exitsCount: Column<String> = text("exits_count")


}




