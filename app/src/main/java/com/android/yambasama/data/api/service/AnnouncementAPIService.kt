package com.android.yambasama.data.api.service

import com.android.yambasama.data.model.api.AnnouncementBody
import com.android.yambasama.data.model.dataRemote.Announcement
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AnnouncementAPIService {

    @GET("/api/announcements")
    suspend fun getAnnouncements(
        @Query("_page")
        page: Int,
        @Query("pagination")
        pagination: Boolean,
        @Query("arrivingTime[strictly_after]")
        arrivingTimeAfter: String,
        @Query("arrivingTime[before]")
        arrivingTimeBefore: String,
        @Query("departureAddress")
        departureAddress: Int,
        @Query("destinationAddress")
        destinationAddress: Int
    ): Response<List<Announcement>>


    @GET("/api/announcement/{id}")
    suspend fun getAnnouncement(
        @Path("id")
        id: Int
    ): Response<Announcement>

    @POST("/api/announcement/create")
    suspend fun createAnnouncement(
        @Body
        announcementBody: AnnouncementBody
    ): Response<String>

}