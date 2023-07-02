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

    data class SearchFormInitAddressDepartureCreated(
        val addressDepartureCreated: Address
    ): SearchFormEvent()

    data class SearchFormInitAddressDestination(
        val addressDestination: Address
    ): SearchFormEvent()

    data class SearchFormInitAddressDestinationCreated(
        val addressDestinationCreated: Address
    ): SearchFormEvent()

    object ErrorDestination: SearchFormEvent()

    object ErrorDestinationCreated: SearchFormEvent()

    object ErrorDeparture: SearchFormEvent()

    object ErrorDepartureCreated: SearchFormEvent()

    data class IsTravelDateUpdated(
        val isTravelDate: Boolean,
        val isDeparture: Boolean,
        val isDestination: Boolean
        ): SearchFormEvent()

    data class IsValidFirstFormStepp(
        val isDepartureCreatedError: Boolean,
        val isDestinationCreatedError: Boolean,
        val isDepartureDateCreatedError: Boolean,
        val isDepartureTimeCreatedError: Boolean,
        val isDestinationDateCreatedError: Boolean,
        val  isDestinationTimeCreatedError: Boolean
    ): SearchFormEvent()

    data class IsValidFinalFormStepp(
        val isPriceCreatedError: Boolean,
        val isNumberOfKgCreatedError: Boolean,
        val isMeetingPlace1CreatedError: Boolean,
        val isMeetingPlace2CreatedError: Boolean
    ): SearchFormEvent()

    data class IsTravelDateCreatedUpdated(
        val isTravelDateCreated: Boolean,
        val isDepartureCreated: Boolean,
        val isDestinationCreated: Boolean
    ): SearchFormEvent()

}