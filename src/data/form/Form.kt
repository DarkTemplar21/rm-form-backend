package com.richmeat.data.form

import org.joda.time.DateTime

data class Form(
    val created_date: DateTime,
    val reviewed_date: DateTime,
    val status: String,
    val created_by: String,
    val reviewed_by: String,
    val anden_1y2_on: Int,
    val anden_1y2_in_range: Int,
    val anden_1y2_reviewed: Int,
    val conservacion_mp_on: Int,
    val conservacion_mp_in_range: Int,
    val conservacion_mp_reviewed: Int,
    val conservacion_pt_on: Int,
    val conservacion_pt_in_range: Int,
    val conservacion_pt_reviewed: Int,
    val anden_3y4_on: Int,
    val anden_3y4_reviewed: Int,
    val anden_3y4_in_range: Int,
    val pasillo_on: Int,
    val pasillo_in_range: Int,
    val pasillo_reviewed: Int,
    val empaque_on: Int,
    val empaque_reviewed: Int,
    val empaque_in_range: Int,
    val preenfriamiento_pt_on: Int,
    val preenfriamiento_pt_in_range: Int,
    val preenfriamiento_pt_reviewed: Int,
    val proceso_on: Int,
    val proceso_in_range: Int,
    val proceso_reviewed: Int,
    val atemperado_mp_on: Int,
    val atemperado_mp_in_range: Int,
    val atemperado_mp_reviewed: Int

)

data class FormDTO(
    val id: Int,
    val created_date: String,
    val reviewed_date: String,
    val status: String,
    val created_by: String,
    val reviewed_by: String,
    val anden_1y2_on: Int,
    val anden_1y2_in_range: Int,
    val anden_1y2_reviewed: Int,
    val conservacion_mp_on: Int,
    val conservacion_mp_in_range: Int,
    val conservacion_mp_reviewed: Int,
    val conservacion_pt_on: Int,
    val conservacion_pt_in_range: Int,
    val conservacion_pt_reviewed: Int,
    val anden_3y4_on: Int,
    val anden_3y4_reviewed: Int,
    val anden_3y4_in_range: Int,
    val pasillo_on: Int,
    val pasillo_in_range: Int,
    val pasillo_reviewed: Int,
    val empaque_on: Int,
    val empaque_reviewed: Int,
    val empaque_in_range: Int,
    val preenfriamiento_pt_on: Int,
    val preenfriamiento_pt_in_range: Int,
    val preenfriamiento_pt_reviewed: Int,
    val proceso_on: Int,
    val proceso_in_range: Int,
    val proceso_reviewed: Int,
    val atemperado_mp_on: Int,
    val atemperado_mp_in_range: Int,
    val atemperado_mp_reviewed: Int

)