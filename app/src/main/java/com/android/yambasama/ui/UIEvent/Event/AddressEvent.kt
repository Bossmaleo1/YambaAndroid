package com.android.yambasama.ui.UIEvent.Event

sealed class AddressEvent {
    data class AddressInit(val value : String, val token: String): AddressEvent()
    data class SearchValueEntered(val value : String,val token: String):AddressEvent()
    object ItemClicked : AddressEvent()
    object IsConnected:AddressEvent()
    object IsError:AddressEvent()
}