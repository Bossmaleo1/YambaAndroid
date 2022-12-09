package com.android.yambasama.data.api.service

import com.android.yambasama.data.model.api.ApiUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AddressAPIService {

    @GET("/api/addresses")
    suspend fun getUser(
        @Query("isoCode")
        isoCode: String,
        @Query("code")
        code: String,
        @Query("airportCode")
        airportCode: String,
        @Query("airportName")
        airportName: String,
        @Query("townName")
        townName: String,
        @Header("Authorization")
        token: String
    ): Response<ApiUserResponse>
}