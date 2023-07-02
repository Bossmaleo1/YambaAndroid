package com.android.yambasama.ui.UIEvent.ScreenState.SearchFormState

import com.android.yambasama.data.model.dataRemote.Address
import java.util.Date

data class SearchFormState (
    var addressDeparture: Address? = null,
    var addressDestination: Address? = null,

    var addressDepartureCreated: Address? = null,
    var addressDestinationCreated: Address? = null,

    var departureOrDestination: Int = 0,
    var dateDialog: Date? = null,
    var dateDialogDepartureCreated: Date? = null,
    var dateDialogDestinationCreated: Date? = null,

    var timeHourDepartureCreated: Int? = null,
    var timeMinuteDepartureCreated: Int? = null,
    var timeHourDestinationCreated: Int? = null,
    var timeMinuteDestinationCreated: Int? = null,

    var arrivingTimeAfter: String? = null,
    var arrivingTimeBefore: String? = null,
    var isDepartureError: Boolean = false,
    var isDestinationError: Boolean = false,
    var isDepartureTimeError: Boolean = false,

    var isDepartureCreatedError: Boolean = false,
    var isDestinationCreatedError: Boolean = false,
    var isDepartureDateCreatedError: Boolean = false,
    var isDestinationDateCreatedError: Boolean = false,
    var isDepartureTimeCreatedError: Boolean = false,
    var isDestinationTimeCreatedError: Boolean = false
 )