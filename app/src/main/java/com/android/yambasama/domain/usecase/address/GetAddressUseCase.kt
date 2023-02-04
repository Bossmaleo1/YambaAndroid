package com.android.yambasama.domain.usecase.address

import com.android.yambasama.data.model.api.ApiAddressResponse
import com.android.yambasama.data.model.api.ApiTokenResponse
import com.android.yambasama.data.util.Resource
import com.android.yambasama.domain.repository.AddressRepository

class GetAddressUseCase(private val addressRepository: AddressRepository) {
    suspend fun execute(
        page: Int,
        pagination: Boolean,
        townName: String,
        token: String
    ): Resource<ApiAddressResponse> {
        return addressRepository.getAddress(page,pagination,townName,token)
    }
}