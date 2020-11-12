package com.richmeat.data.model.form

import com.richmeat.data.model.user.Users
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object Forms: Table() {
    val created_date: Column<DateTime> = datetime("created_date")
    val reviewed_date: Column<DateTime> = datetime("reviewed_date")
    val status: Column<String>  = text("status")
    val created_by: Column<String>  = text("created_by")
    val reviewed_by: Column<String>  = text("reviewed_by")
    val anden_1y2_on: Column<Int> = integer("anden_1y2_on")
    val anden_1y2_in_range: Column<Int> = integer("anden_1y2_in_range")
    val anden_1y2_reviewed: Column<Int> = integer("anden_1y2_reviewed")
    val conservacion_mp_on: Column<Int> = integer("conservacion_mp_on")
    val conservacion_mp_in_range: Column<Int> = integer("conservacion_mp_in_range")
    val conservacion_mp_reviewed: Column<Int> = integer("conservacion_mp_reviewed")
    val conservacion_pt_on: Column<Int> = integer("conservacion_pt_on")
    val conservacion_pt_in_range: Column<Int> = integer("conservacion_pt_in_range")
    val conservacion_pt_reviewed: Column<Int> = integer("conservacion_pt_reviewed")
    val anden_3y4_on: Column<Int> = integer("anden_3y4_on")
    val anden_3y4_reviewed: Column<Int> = integer("anden_3y4_reviewed")
    val anden_3y4_in_range: Column<Int> = integer("anden_3y4_in_range")
    val pasillo_on: Column<Int> = integer("pasillo_on")
    val pasillo_in_range: Column<Int> = integer("pasillo_in_range")
    val pasillo_reviewed: Column<Int> = integer("pasillo_reviewed")
    val empaque_on: Column<Int> = integer("empaque_on")
    val empaque_reviewed: Column<Int> = integer("empaque_reviewed")
    val empaque_in_range: Column<Int> = integer("empaque_in_range")
    val preenfriamiento_pt_on: Column<Int> = integer("preenfriamiento_pt_on")
    val preenfriamiento_pt_in_range: Column<Int> = integer("preenfriamiento_pt_in_range")
    val preenfriamiento_pt_reviewed: Column<Int> = integer("preenfriamiento_pt_reviewed")
    val proceso_on: Column<Int> = integer("proceso_on")
    val proceso_in_range: Column<Int> = integer("proceso_in_range")
    val proceso_reviewed: Column<Int> = integer("proceso_reviewed")
    val atemperado_mp_on: Column<Int> = integer("atemperado_mp_on")
    val atemperado_mp_in_range: Column<Int> = integer("atemperado_mp_in_range")
    val atemperado_mp_reviewed: Column<Int> = integer("atemperado_mp_reviewed")
}