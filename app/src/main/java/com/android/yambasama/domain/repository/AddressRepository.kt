package com.android.yambasama.domain.repository


import com.android.yambasama.data.model.dataRemote.Address
import com.android.yambasama.data.util.Resource

interface AddressRepository {

    suspend fun getAddress(
        locale: String,
        page: Int,
        query: String
    ): Resource<List<Address>>

}