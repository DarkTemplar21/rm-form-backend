package com.richmeat.data.model

import org.jetbrains.exposed.sql.Column
import org.joda.time.DateTime
import java.util.*

data class Productivity(


    val startDate: String?,
    val date : DateTime,
    val turnNumber : Int,
    var M1X400 : Int,
    var M1X800 : Int,
    var M01 : Int,
    var M02 : Int,
    var M03 : Int,
    var M04 : Int,
    var M05 : Int,
    var MCEDA : Int




)
