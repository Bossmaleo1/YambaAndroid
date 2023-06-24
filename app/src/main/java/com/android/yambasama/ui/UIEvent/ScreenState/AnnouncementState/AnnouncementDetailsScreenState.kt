package com.android.yambasama.ui.UIEvent.ScreenState.AnnouncementState

import com.android.yambasama.data.model.dataRemote.Announcement

data class AnnouncementDetailsScreenState(
    var isNetworkConnected: Boolean = true,
    var isLoad: Boolean = true,
    var isNetworkError: Boolean = false,
    var announcementDetails: Announcement? = null,
    var refreshing: Boolean = false
)