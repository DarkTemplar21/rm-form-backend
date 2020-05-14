package com.richmeat.data.model

import org.jetbrains.exposed.sql.Column
import org.joda.time.DateTime
import java.util.*

data class Productivity(
    val id: Int,
    val key: Int,
    val boxCant: Int



)
// request object
data class ProductivityDTO(
    val key: Int,
    val boxCant: Int

)