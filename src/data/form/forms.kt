package com.richmeat.data.form

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object TemperatureForms: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val created_date: Column<DateTime> = datetime("created_date")
    val reviewed_date: Column<DateTime> = datetime("reviewed_date")
    val status: Column<String>  = text("status")
    val created_by: Column<String>  = text("created_by")
    val reviewed_by: Column<String>  = text("reviewed_by")



}