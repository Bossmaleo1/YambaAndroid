package com.android.yambasama.ui.UIEvent.ScreenState.AnnouncementState

import com.android.yambasama.data.model.api.AnnouncementBody

data class AnnouncementCreateScreenState(
    var isNetworkConnected: Boolean = true,
    var isLoad: Boolean = true,
    var isNetworkError: Boolean = false,
    var announcementBody: AnnouncementBody? = null,
)
