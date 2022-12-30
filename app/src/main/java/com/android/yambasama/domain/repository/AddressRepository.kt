package com.android.yambasama.domain.repository

import com.android.yambasama.data.model.api.ApiAddressResponse
import com.android.yambasama.data.model.api.ApiUserResponse
import com.android.yambasama.data.util.Resource

interface AddressRepository {

    suspend fun getAddress(
        page: Int,
        pagination: Boolean,
        isoCode: String,
        code: String,
        airportName: String,
        airportCode: String,
        townName: String,
        token: String
    ): Resource<ApiAddressResponse>

}