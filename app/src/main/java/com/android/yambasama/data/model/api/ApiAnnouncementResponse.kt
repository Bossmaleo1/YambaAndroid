package com.android.yambasama.data.model.api

import com.android.yambasama.data.model.dataRemote.Address
import com.android.yambasama.data.model.dataRemote.Announcement
import com.google.gson.annotations.SerializedName

data class ApiAnnouncementResponse (@SerializedName("hydra:member") val annoucement: List<Announcement>)