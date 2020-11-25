package com.richmeat.data.coldRoom

data class ColdRoom(
    val temperatureFormId: Int,
    val name: String,
    val temperatureRange: String,
    val isOn: Int,
    val inRange: Int,
    val isReviewed: Int

)