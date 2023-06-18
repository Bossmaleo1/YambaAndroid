package com.android.yambasama.data.repository

import com.android.yambasama.data.model.api.ApiAddressResponse
import com.android.yambasama.data.model.dataRemote.Address
import com.android.yambasama.data.repository.dataSource.address.AddressRemoteDataSource
import com.android.yambasama.data.util.Resource
import com.android.yambasama.domain.repository.AddressRepository
import retrofit2.Response

class AddressRepositoryImpl(
    private val addressRemoteDataSource: AddressRemoteDataSource
): AddressRepository {

    private fun responseToResourceAddress(response: Response<List<Address>>): Resource<List<Address>> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getAddress(
        page: Int,
        query: String,
        token: String
    ): Resource<List<Address>> {
        return responseToResourceAddress(
            addressRemoteDataSource.getAddress(
                page,
                query,
                token
            )
        )
    }
}