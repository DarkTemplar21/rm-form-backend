package com.richmeat.data.model

import org.jetbrains.exposed.sql.Column
import org.joda.time.DateTime
import java.util.*

data class Productivity(


    val startDate: String?,
    val date : String,
    val turnNumber : Int,
    val M1X400 : Int,
    val M1X800 : Int,
    val M01 : Int,
    val M02 : Int,
    val M03 : Int,
    val M04 : Int,
    val M05 : Int,
    val MCEDA : Int




)
