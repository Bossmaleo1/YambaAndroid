package com.android.yambasama.ui.UIEvent.ScreenState.AnnouncementState

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.android.yambasama.data.model.dataRemote.Announcement
import kotlinx.coroutines.flow.MutableStateFlow

data class AnnouncementScreenState(
    var isNetworkConnected: Boolean = true,
    var isLoad: Boolean = true,
    var isNetworkError: Boolean = false,
    var currentPage: Int = 1,
    var announcement: List<Announcement> = listOf(),
    var announcementList: SnapshotStateList<Announcement> = mutableStateListOf(),
    var announcementListTemp: SnapshotStateList<Announcement> = mutableStateListOf(),
    var initCall: Int = 0,
    var refreshing: Boolean = false,
    var isEmptyAnnouncement: Boolean = false
)