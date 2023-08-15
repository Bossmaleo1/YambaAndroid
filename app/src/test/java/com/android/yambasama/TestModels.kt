package com.android.yambasama

import com.android.yambasama.data.model.dataRemote.Address
import com.android.yambasama.data.model.dataRemote.Announcement
import com.android.yambasama.data.model.dataRemote.User
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

val addressesTest = listOf<Address>(
    Address(
        id = 93,
        isoCode = "COG",
        airportCode = "PNR",
        airportName = "Agostinho-Neto",
        townName = "Pointe-Noire",
        code = "CG"
    ),
    Address(
        id = 94,
        isoCode = "COG",
        airportCode = "BZV",
        airportName = "Maya-Maya",
        townName = "Brazzaville",
        code = "CG"
    ),
    Address(
        id = 95,
        isoCode = "GAB",
        code = "GA",
        airportCode = "LBV",
        airportName = "Léon Mba",
        townName = "Libreville"
    ),
    Address(
        id = 96,
        isoCode = "GAB",
        code = "GA",
        airportCode = "POG",
        airportName = "Port-Gentil",
        townName = "Port-Gentil"
    ),
    Address(
        id = 97,
        isoCode = "FRA",
        code = "FR",
        airportCode = "CDG",
        airportName = "Roissy-Charles-de-Gaulle",
        townName = "Paris"
    ),
    Address(
        id = 98,
        isoCode = "FRA",
        code = "FR",
        airportCode = "ORY",
        airportName = "Orly",
        townName = "Paris"
    ),
    Address(
        id = 99,
        isoCode = "FRA",
        code = "FR",
        airportCode = "LBG",
        airportName = "Le Bourget",
        townName = "Paris"
    ),
    Address(
        id = 100,
        isoCode = "FRA",
        code = "FR",
        airportCode = "MRS",
        airportName = "Marseille-Provence",
        townName = "Marseille"
    ),
    Address(
        id = 101,
        isoCode = "FRA",
        code = "FR",
        airportCode = "LYS",
        airportName = "Lyon-Saint-Exupéry",
        townName = "Lyon"
    ),
    Address(
        id = 102,
        isoCode = "ARE",
        code = "AE",
        airportCode = "DXB",
        airportName = "Dubaï",
        townName = "Dubaï"
    ),

)

val fakeUser: User = User(
    id = 34,
    email = "sidneymaleoregis@gmail.com",
    username = "sidneymaleoregis@gmail.com",
    firstName = "Sidney",
    lastName = "MALEO",
    phone = "+242068125204",
    state = "test",
    sex = "M",
    images = listOf(),
    roles = listOf(),
    pushNotifications = listOf(),
    nationality = ""
)

val users = listOf<User>(
    fakeUser
)

val dateFormatArrivingString1 = "2023-03-01T21:00:00+00:00"
val formatterArrivingTime1: DateFormat = SimpleDateFormat(dateFormatArrivingString1)
val myDateArrivingTime1: Date = formatterArrivingTime1.parse(dateFormatArrivingString1) as Date


val announcements = listOf<Announcement>(
    Announcement(
       id = 501,
       arrivingTime = myDateArrivingTime1,
       price = 76F,
       user = fakeUser,
       status = listOf(),
       numberOfKgs = listOf(),
       published = Date(),
       meetingPlaces1 = "meting Place 1",
       meetingPlaces2 = "meeting Place 2",
       departureTime = Date()
    )
)