package com.android.yambasama.ui.UIEvent.ScreenState.AnnouncementState

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.android.yambasama.data.model.dataRemote.Announcement

data class AnnouncementScreenState(
    var isNetworkConnected: Boolean = true,
    var isLoad: Boolean = false,
    var isNetworkError: Boolean = false,
    var currentPage: Int = 1,
    var announcementList: MutableList<Announcement> = mutableListOf(),
    var announcementListTemp: MutableList<Announcement> = mutableListOf(),
    var initCall: Int = 0,
)