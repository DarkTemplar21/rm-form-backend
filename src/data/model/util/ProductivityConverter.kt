package com.richmeat.data.model.util

import com.richmeat.data.model.Productivity

class ProductivityConverter {
    companion object{
        fun fromProductivityKtorToProductivityAndroid(productivityKtor : Productivity): ProductivityAndroid{

            val productivityAndroid = ProductivityAndroid(productivityKtor.startDate
                ,productivityKtor.date.toString(),productivityKtor.turnNumber
                ,productivityKtor.M1X400,productivityKtor.M1X800,productivityKtor.M01,productivityKtor.M02,
            productivityKtor.M03,productivityKtor.M04,productivityKtor.M05,productivityKtor.MCEDA)
            return productivityAndroid
        }
    }
}