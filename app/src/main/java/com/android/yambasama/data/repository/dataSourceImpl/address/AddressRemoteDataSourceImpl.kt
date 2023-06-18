package com.android.yambasama.data.repository.dataSourceImpl.address

import com.android.yambasama.data.api.service.AddressAPIService
import com.android.yambasama.data.model.api.ApiAddressResponse
import com.android.yambasama.data.model.dataRemote.Address
import com.android.yambasama.data.repository.dataSource.address.AddressRemoteDataSource
import retrofit2.Response

class AddressRemoteDataSourceImpl(
    private val addressAPIService: AddressAPIService
) : AddressRemoteDataSource {

    override suspend fun getAddress(
        locale: String,
        page: Int,
        query: String,
        token: String
    ): Response<List<Address>> {
        return addressAPIService.getAddress(
            locale,
            page,
            query,
            token
        )
    }


}