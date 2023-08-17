package com.android.yambasama.domain.repository

import com.android.yambasama.data.model.dataRemote.Address
import com.android.yambasama.data.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first

class FakeAddressRepository: AddressRepository {

    private var shouldReturnNetworkError = false

    private val _savedAddress = MutableStateFlow(LinkedHashMap<String, Address>())
    val savedAddress: StateFlow<LinkedHashMap<String, Address>> = _savedAddress.asStateFlow()

    private val observableAddressList: Flow<List<Address>> = savedAddress.map {
        if (shouldReturnNetworkError) {
            throw Exception("Test exception")
        } else {
            it.values.toList()
        }
    }

    fun setShouldThrowError(value: Boolean) {
        shouldReturnNetworkError = value
    }


    override suspend fun getAddress(
        locale: String,
        page: Int,
        query: String,
        token: String
    ): Resource<List<Address>> {
        return if (shouldReturnNetworkError) {
            Resource.Error("Error", null)
        } else {
            Resource.Success(observableAddressList.first())
        }
    }


}