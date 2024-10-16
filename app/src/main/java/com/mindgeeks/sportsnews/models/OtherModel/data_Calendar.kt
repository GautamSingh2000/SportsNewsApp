package com.mindgeeks.sportsnews.models.OtherModel

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class data_Calendar(var date: Date, var isSelected : Boolean = false)
{
    val CalanderDay : String
        get() = SimpleDateFormat("EE", Locale.getDefault()).format(date)

    val CalenderDate : String
        get() {
            val cal = Calendar.getInstance()
            cal.time = date
            return cal[Calendar.DAY_OF_MONTH].toString()
        }
}
