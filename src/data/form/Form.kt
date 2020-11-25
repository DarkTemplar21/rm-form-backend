package com.richmeat.data.form

import com.richmeat.data.coldRoom.ColdRoom
import org.joda.time.DateTime

data class Form(
    val created_date: DateTime,
    val reviewed_date: DateTime,
    val status: String,
    val created_by: String,
    val reviewed_by: String,
    val coldRooms: List<ColdRoom>
)

data class FormDTO(
    val id: Int,
    val createdDate: String,
    val reviewedDate: String,
    val status: String,
    val createdBy: String,
    val reviewedBy: String,
    var coldRooms: List<ColdRoom>
)