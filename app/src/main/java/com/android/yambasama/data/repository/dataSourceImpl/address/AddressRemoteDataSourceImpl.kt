package com.android.yambasama.data.repository.dataSourceImpl.address

import com.android.yambasama.data.api.service.AddressAPIService
import com.android.yambasama.data.model.api.ApiAddressResponse
import com.android.yambasama.data.repository.dataSource.address.AddressRemoteDataSource
import retrofit2.Response

class AddressRemoteDataSourceImpl(
    private val addressAPIService: AddressAPIService
) : AddressRemoteDataSource {

    override suspend fun getAddress(
        page: Int,
        pagination: Boolean,
        townName: String,
        token: String
    ): Response<ApiAddressResponse> {
        return addressAPIService.getAddress(
            page,
            pagination,
            townName,
            token
        )
    }


}