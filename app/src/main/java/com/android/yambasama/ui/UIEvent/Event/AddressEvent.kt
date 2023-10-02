package com.android.yambasama.ui.UIEvent.Event

import android.content.Context

sealed class AddressEvent {
    data class AddressInit(
        val app: Context,
        val locale: String,
        val value : String,
        ): AddressEvent()
    data class SearchValueEntered(
        val locale: String,
        val value : String): AddressEvent()
    object ItemClicked : AddressEvent()
    data class IsNetworkConnected(val errorMessage: String): AddressEvent()
    object InitAddressState:AddressEvent()
    data class IsNetworkError(val errorMessage: String): AddressEvent()

    data class IsInternalError(val errorMessage: String): AddressEvent()
}
