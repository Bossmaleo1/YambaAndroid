package com.android.yambasama.ui.util

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

    fun getDateTimeFormatter(date: Date): String { //"dd/MM/yyyy  HH:mm"
        val formatter: SimpleDateFormat = SimpleDateFormat("EEE d MMM yy  HH:mm", Locale.getDefault())
        return formatter.format(date)
    }

}