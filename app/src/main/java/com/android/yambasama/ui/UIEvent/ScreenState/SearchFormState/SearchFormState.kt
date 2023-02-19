package com.android.yambasama.ui.UIEvent.ScreenState.SearchFormState

import com.android.yambasama.data.model.dataRemote.Address

data class SearchFormState (
    var addressDeparture: Address? = null,
    var addressDestination: Address? = null,
    var departureOrDestination: Int = 0,
    var dateDialog: DateDialog? = null
 )