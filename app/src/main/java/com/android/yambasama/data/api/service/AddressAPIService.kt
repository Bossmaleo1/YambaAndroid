package com.android.yambasama.data.api.service

import com.android.yambasama.data.model.dataRemote.Address
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AddressAPIService {

    @GET("/api/address/search")
    suspend fun getAddress(
        @Query("locale")
        locale: String,
        @Query("page")
        page: Int,
        @Query("q")
        query: String
    ): Response<List<Address>>
}