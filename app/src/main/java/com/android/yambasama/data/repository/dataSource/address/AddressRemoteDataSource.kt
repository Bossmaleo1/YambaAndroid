package com.android.yambasama.data.repository.dataSource.address

import com.android.yambasama.data.model.api.ApiAddressResponse
import com.android.yambasama.data.model.dataRemote.Address
import retrofit2.Response

interface AddressRemoteDataSource {
    suspend fun getAddress(
        locale: String,
        page: Int,
        query: String
    ): Response<List<Address>>
}