package com.android.yambasama.ui.UIEvent.ScreenState.AddressScreenState

import com.android.yambasama.data.model.dataRemote.Address

data class AddressScreenState(
    var isNetworkConnected: Boolean = true,
    var isLoad: Boolean = false,
    var isNetworkError: Boolean = false,
    var currentPage: Int = 1,
    var initCall: Int = 0,
    var addressList: List<Address> = mutableListOf(),
    var addressListTemp: List<Address> = mutableListOf(),
    var searchInputValue: String = ""
)
