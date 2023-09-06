package com.android.yambasama.ui.views.bottomnavigationviews.annoucement.announcementlist

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.android.yambasama.presentation.viewModel.announcement.AnnouncementViewModel
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModel
import com.android.yambasama.presentation.viewModel.user.UserViewModel
import com.android.yambasama.ui.views.bottomnavigationviews.annoucement.announcementDetails.announcementlist.AnnouncementItem
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.snapshots.SnapshotMutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.android.yambasama.R
import com.android.yambasama.data.model.dataRemote.Announcement
import com.android.yambasama.ui.util.Util
import com.android.yambasama.ui.views.shimmer.AnnouncementShimmer
import com.android.yambasama.ui.views.viewsError.networkError
import com.android.yambasama.ui.views.viewsMessage.emptyMessage

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfiniteAnnouncementList(
    navController: NavHostController,
    userViewModel: UserViewModel,
    searchFormViewModel: SearchFormViewModel,
    announcementViewModel: AnnouncementViewModel,
    paddingValues: PaddingValues,
    country: Util,
    listState: LazyListState,
    listItems: SnapshotStateList<Announcement>,
    refreshing: Boolean
) {

    val screenState = announcementViewModel.screenState.value
    val scaffoldState = rememberScaffoldState()
    val screenStateUser = userViewModel.screenState.value


    LazyColumn(
        contentPadding = paddingValues,
        state = listState
    ) {
        if (!refreshing) {
            items(listItems) { announcement ->
                AnnouncementItem(
                    navController = navController,
                    announcement = announcement,
                    searchFormViewModel = searchFormViewModel,
                    annoucementViewModel = announcementViewModel,
                    util = country
                )
            }
        }


        if (screenState.isLoad) {
            items(count = 1) {
                AnnouncementShimmer(7)
            }
        }

        if (!screenState.isNetworkConnected) {
            items(count = 1) {
                networkError(
                    title = stringResource(R.string.network_error),
                    iconValue = 0
                )
            }
        } else if (screenState.isNetworkError && !screenState.isLoad) {
            items(count = 1) {
                networkError(
                    title = stringResource(R.string.is_connect_error),
                    iconValue = 1
                )
            }
        } else if(screenState.isEmptyAnnouncement && !screenState.isLoad) {
            items(count = 1) {
                emptyMessage(
                    title = stringResource(R.string.empty_announcement),
                    iconValue = 2
                )
            }
        }


    }



    listState.OnBottomReached(buffer = 2) {
        screenState.isLoad = true
        announcementViewModel.screenState.value.currentPage++
        searchFormViewModel.screenState.value.arrivingTimeAfter?.let {
            searchFormViewModel.screenState.value.arrivingTimeBefore?.let { it1 ->
                searchFormViewModel.screenState.value.addressDestination?.id?.let { it2 ->
                    searchFormViewModel.screenState.value.addressDeparture?.id?.let { it3 ->
                        announcementViewModel.getAnnouncements(
                            arrivingTimeAfter = it,
                            arrivingTimeBefore = it1,
                            destinationAddressId = it2,
                            departureAddressId = it3
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun LazyListState.OnBottomReached(
    buffer: Int = 0,
    loadMore : () -> Unit
){
    // Buffer must be positive.
    // Or our list will never reach the bottom.
    require(buffer >= 0) { "buffer cannot be negative, but was $buffer" }

    // state object which tells us if we should load more
    val shouldLoadMore = remember {
        derivedStateOf {
            // get last visible item
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?:
                // list is empty
                // return false here if loadMore should not be invoked if the list is empty
                return@derivedStateOf true
            // Check if last visible item is the last item in the list
            lastVisibleItem.index == layoutInfo.totalItemsCount - 1 - buffer
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect {
                // if should load more, then invoke loadMore
                if (it) loadMore()
            }
    }
}