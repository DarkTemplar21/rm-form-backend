package com.richmeat.data.coldRoom

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ColdRooms: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = text("name")
    val temperature_form_id: Column<Int> = integer("temperature_form_id")
    val temperature_range: Column<String> = text("temperature_range")
    val is_on: Column<Int> = integer("is_on")
    val in_range: Column<Int> = integer("in_range")
    val is_reviewed: Column<Int> = integer("is_reviewed")

}