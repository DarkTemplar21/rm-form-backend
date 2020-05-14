package com.richmeat.data.model

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import java.util.*



object Productivities: Table() {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val key: Column<Int> = integer("key")
    val boxCant: Column<Int> = integer("box_cant")


}




