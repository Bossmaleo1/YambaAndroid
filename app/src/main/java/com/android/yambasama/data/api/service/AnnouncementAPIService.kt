package com.android.yambasama.data.api.service

import com.android.yambasama.data.model.api.ApiAnnouncementResponse
import com.android.yambasama.data.model.dataRemote.Announcement
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AnnouncementAPIService {

    @GET("/api/announcements")
    suspend fun getAnnouncements(
        @Query("_page")
        page: Int,
        @Query("pagination")
        pagination: Boolean,
        @Query("departureTime")
        departureTime: String,
        @Query("departureAddress")
        departureAddress: String,
        @Query("destinationAddress")
        destinationAddress: String,
        @Header("Authorization")
        token: String
    ): Response<ApiAnnouncementResponse>

}