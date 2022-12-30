package com.android.yambasama.data.model.api

import com.android.yambasama.data.model.dataRemote.Address
import com.google.gson.annotations.SerializedName

data class ApiAddressResponse(
    @SerializedName("hydra:member")
    val address: List<Address>
)
