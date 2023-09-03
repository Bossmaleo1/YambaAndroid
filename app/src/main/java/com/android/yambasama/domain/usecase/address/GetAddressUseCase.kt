package com.android.yambasama.domain.usecase.address

import com.android.yambasama.data.model.dataRemote.Address
import com.android.yambasama.data.util.Resource
import com.android.yambasama.domain.repository.AddressRepository

class GetAddressUseCase(private val addressRepository: AddressRepository) {
    suspend fun execute(
        locale: String,
        page: Int,
        query: String
    ): Resource<List<Address>> {
        return addressRepository.getAddress(locale,page,query)
    }
}