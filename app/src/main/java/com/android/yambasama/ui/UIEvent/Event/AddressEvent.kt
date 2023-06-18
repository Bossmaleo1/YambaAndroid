package com.android.yambasama.ui.UIEvent.Event

sealed class AddressEvent {
    data class AddressInit(
        val locale: String,
        val value : String,
        val token: String
        ): AddressEvent()
    data class SearchValueEntered(
        val locale: String,
        val value : String,
        val token: String):AddressEvent()
    object ItemClicked : AddressEvent()
    object IsNetworkConnected:AddressEvent()
    object InitAddressState:AddressEvent()
    object IsNetworkError:AddressEvent()
}
