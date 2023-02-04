package com.android.yambasama.data.repository.dataSource.address

import com.android.yambasama.data.model.api.ApiAddressResponse
import retrofit2.Response

interface AddressRemoteDataSource {
    suspend fun getAddress(
        page: Int,
        pagination: Boolean,
        townName: String,
        token: String
    ): Response<ApiAddressResponse>
}