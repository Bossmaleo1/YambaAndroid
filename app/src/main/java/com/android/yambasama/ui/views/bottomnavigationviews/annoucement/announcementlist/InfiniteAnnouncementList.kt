package com.android.yambasama.ui.views.bottomnavigationviews.annoucement.announcementlist

import android.os.Build
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
import com.android.yambasama.R
import com.android.yambasama.data.model.dataRemote.Announcement
import com.android.yambasama.ui.UIEvent.Event.AnnouncementEvent
import com.android.yambasama.ui.util.Util
import com.android.yambasama.ui.views.shimmer.AnnouncementShimmer
import com.android.yambasama.ui.views.viewsError.networkError

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
    listItems: List<Announcement>,
) {

    val screenState = announcementViewModel.screenState.value
    val scaffoldState = rememberScaffoldState()
    val screenStateUser = userViewModel.screenState.value

    LazyColumn(
        contentPadding = paddingValues,
        state = listState
    ) {

        items(listItems) { announcement ->
            AnnouncementItem(
                navController = navController,
                announcement = announcement,
                searchFormViewModel = searchFormViewModel,
                annoucementViewModel = announcementViewModel,
                util = country
            )
        }

        //if (screenState.isLoad) {
            items(count = 1) {
                AnnouncementShimmer(7)
            }
        //}

        if (!screenState.isNetworkConnected) {
            items(count = 1) {
                networkError(
                    title = stringResource(R.string.network_error),
                    iconValue = 0
                )
            }
        } else if (screenState.isNetworkError) {
            items(count = 1) {
                networkError(
                    title = stringResource(R.string.is_connect_error),
                    iconValue = 1
                )
            }
        }


    }



    listState.OnBottomReached(buffer = 2) {
        screenState.isLoad = true
        screenState.currentPage++
        announcementViewModel.getAnnouncement(
            token = screenStateUser.tokenRoom[0].token,
            departureTimeAfter = "",
            departureTimeBefore = "",
            destinationAddressId = 0,
            departureAddressId = 0
        )
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