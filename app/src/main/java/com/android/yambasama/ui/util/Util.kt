package com.android.yambasama.ui.util

import com.android.yambasama.data.model.dataRemote.NumberOfKg
import java.text.SimpleDateFormat
import java.util.*

class Util {

    fun getCountry(code: String): String {
        val listCountries = Locale.getISOCountries()
        listCountries.forEach { country ->
            val locale = Locale(Locale.getDefault().isO3Language, country)
            if ((locale.country.lowercase()) == code.lowercase()) {
               return locale.displayCountry
            }
        }
        return ""
    }

    fun getDateFormatter(date: Date): String {
        val formatter: SimpleDateFormat = SimpleDateFormat("EEE d MMM yy", Locale.getDefault())
        return formatter.format(date)
    }

    fun getNumberOfKg(numberOfKgs: List<NumberOfKg>): Float {
        if(numberOfKgs.isEmpty()) {
            return 0F
        }
        return numberOfKgs.filter { numberOfKg -> !numberOfKg.status }[0].numberOfKg
    }

    fun getDateTimeFormatter(date: Date): String { //"dd/MM/yyyy  HH:mm"
        val formatter: SimpleDateFormat = SimpleDateFormat("EEE d MMM yy  HH:mm", Locale.getDefault())
        return formatter.format(date)
    }

    fun getDateDisplayingStart(date: Date): String {
        val cal: Calendar =  Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        cal.time = date
        val monthCount = (cal.get(Calendar.MONTH)+1)
        val monthDay: String = if (monthCount >= 10) {
            "${(cal.get(Calendar.MONTH)+1)}"
        } else {
            "0${(cal.get(Calendar.MONTH)+1)}"
        }
        return "${cal.get(Calendar.YEAR)}-${monthDay}-${cal.get(Calendar.DAY_OF_MONTH)}T00:00:00"
    }

    fun getDateDisplayingEnd(date: Date): String {
        val cal: Calendar =  Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        cal.time = date
        val monthCount = (cal.get(Calendar.MONTH)+1)
        val monthDay: String = if (monthCount >= 10) {
            "${(cal.get(Calendar.MONTH)+1)}"
        } else {
            "0${(cal.get(Calendar.MONTH)+1)}"
        }
        return "${cal.get(Calendar.YEAR)}-${monthDay}-${cal.get(Calendar.DAY_OF_MONTH)}T23:59:00"
    }

}