package com.richmeat.data.util

import org.joda.time.DateTime

object DateHelper {


       fun getStringDate(dateTime: DateTime): String{
              return dateTime.toString(DATE_FORMAT)
       }
       fun getStringShortDate(dateTime: DateTime): String{
              return dateTime.toString(SHORT_FORMAT)
       }
       fun getStringHour(dateTime: DateTime): String{
              return dateTime.toString(HOUR_FORMAT)
       }
       fun getStringDay(dateTime: DateTime): String{
              return dateTime.toString(DAY_FORMAT)
       }

       public val DATE_FORMAT = "dd-MM-yyyy HH:mm:ss"
       public val SHORT_FORMAT = "dd-MM-yyyy"
       public val HOUR_FORMAT = "HH:mm:ss"
       public val DAY_FORMAT = "dd"

}