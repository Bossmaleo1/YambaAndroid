package com.android.yambasama.ui.util

import com.android.yambasama.data.model.dataRemote.NumberOfKg
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration.Companion.hours

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

    fun getTimeFormatter(hour: Int, minute: Int): String {
        val formatter: SimpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)
        cal.isLenient = false
        return formatter.format(cal.time)
    }

    fun getTimeFormatterSimple(hour: Int, minute: Int): String {
        return "$hour:$minute"
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
        val cal: Calendar =  Calendar.getInstance(TimeZone.getTimeZone(TimeZone.getDefault().id))
        cal.time = date
        val monthCount = (cal.get(Calendar.MONTH)+1)
        val monthDay: String = if (monthCount >= 10) {
            "${(cal.get(Calendar.MONTH)+1)}"
        } else {
            "0${(cal.get(Calendar.MONTH)+1)}"
        }
        return "${cal.get(Calendar.YEAR)}-${monthDay}-${cal.get(Calendar.DAY_OF_MONTH)}T00:00:00"
    }

    fun getAnnouncementDate(
        dateAnnouncement: Date,
        hourAnnouncement: Int,
        minuteAnnouncement: Int
    ): String {
        val calAnnouncementDate: Calendar =  Calendar.getInstance(TimeZone.getTimeZone(TimeZone.getDefault().id))
        val calAnnouncementTime: Calendar =  Calendar.getInstance(TimeZone.getTimeZone(TimeZone.getDefault().id))
        calAnnouncementDate.time = dateAnnouncement
        //calAnnouncementTime.time = timeAnnouncement
        val monthCount = (calAnnouncementDate.get(Calendar.MONTH) +1)
        val monthDay: String = if (monthCount >= 10) {
            "${(calAnnouncementDate.get(Calendar.MONTH)+1)}"
        } else {
            "0${(calAnnouncementDate.get(Calendar.MONTH)+1)}"
        }
        calAnnouncementTime.set(Calendar.HOUR_OF_DAY, hourAnnouncement)
        calAnnouncementTime.set(Calendar.MINUTE, minuteAnnouncement)
        val hourCount : String = "${calAnnouncementTime[Calendar.HOUR_OF_DAY]}"
        val minuteCount: String = "${calAnnouncementTime[Calendar.MINUTE]}"
        return "${calAnnouncementDate.get(Calendar.YEAR)}-${monthDay}-${calAnnouncementDate.get(Calendar.DAY_OF_MONTH)}T${hourCount}:${minuteCount}:00"
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