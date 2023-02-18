package com.android.yambasama.ui.UIEvent.Event

import com.android.yambasama.data.model.dataRemote.Address

sealed class SearchFormEvent {
    data class SearchFormInit(
        val addressDeparture: Address,
        val addressDestination: Address
    ): SearchFormEvent()

    data class SearchFormInitAddressDeparture(
        val addressDeparture: Address
    ): SearchFormEvent()

    data class SearchFormInitAddressDestination(
        val addressDestination: Address
    ): SearchFormEvent()

}