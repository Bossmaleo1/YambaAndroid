package com.android.yambasama.ui.UIEvent

sealed class UIEvent {
    data class ShowMessage(val message: String): UIEvent()
}
