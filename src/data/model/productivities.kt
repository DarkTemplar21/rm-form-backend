package com.richmeat.data.model

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import java.util.*



object Productivities: Table() {
    val id: Column<UUID> = uuid("id").autoIncrement().primaryKey()
    val turnNumber: Column<Int> = integer("turn_number")
    val startDate: Column<String> = text("start_date")
    val date: Column<DateTime> = datetime("date")
    val M1X400: Column<Int> = integer("m1x400")
    val M1X800: Column<Int> = integer("m1x800")
    val M01: Column<Int> = integer("m01")
    val M02: Column<Int> = integer("m02")
    val M03: Column<Int> = integer("m03")
    val M04: Column<Int> = integer("m04")
    val M05: Column<Int> = integer("m05")
    val MCEDA: Column<Int> = integer("mceda")


}






