package com.android.yambasama.ui.views.bottomnavigationviews.annoucement.announcementDetails.announcementlist

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.yambasama.R
import com.android.yambasama.presentation.viewModel.announcement.AnnouncementViewModel
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModel
import com.android.yambasama.presentation.viewModel.user.UserViewModel
import com.android.yambasama.ui.UIEvent.Event.AnnouncementEvent
import com.android.yambasama.ui.UIEvent.UIEvent
import com.android.yambasama.ui.util.Util
import com.android.yambasama.ui.views.bottomnavigationviews.annoucement.announcementlist.InfiniteAnnouncementList
import com.android.yambasama.ui.views.shimmer.AnnouncementShimmer
import com.android.yambasama.ui.views.utils.OnBottomReached
import com.android.yambasama.ui.views.viewsError.networkError
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun AnnouncementView(
    navController: NavHostController,
    userViewModel: UserViewModel,
    searchFormViewModel: SearchFormViewModel,
    announcementViewModel: AnnouncementViewModel,
    listState: LazyListState
) {
    val scaffoldState = rememberScaffoldState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val screenState = announcementViewModel.screenState.value
    val screenStateUser = userViewModel.screenState.value
    val country = Util()
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)
    val isDark = isSystemInDarkTheme()

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "",
                            tint = if (!isDark) {
                                colorResource(R.color.black40)
                            } else {
                                Color.White
                            }
                        )
                    }
                },
                actions = {

                },
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        text = stringResource(R.string.announcements),
                        color = if (!isDark) {
                            colorResource(R.color.black40)
                        } else {
                            Color.White
                        },
                        fontWeight = FontWeight.Normal
                    )
                }
            )
        },
        content = { innerPadding ->

            LaunchedEffect(key1 = true) {
                if (screenState.currentPage == 1 && !isRefreshing && screenState.announcementList.size == 0) {
                    searchFormViewModel.screenState.value.addressDeparture?.id?.let {
                        searchFormViewModel.screenState.value.addressDestination?.id?.let { it1 ->
                            AnnouncementEvent.AnnouncementInt(
                                token = screenStateUser.tokenRoom[0].token,
                                destinationAddressId = it1,
                                departureAddressId = it,
                                departureTimeAfter = "2023-02-01T00:00:00",
                                departureTimeBefore = "2023-02-01T23:59:00"
                            )
                        }
                    }?.let {
                        announcementViewModel.onEvent(
                            it
                        )
                    }

                }

            }

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    isRefreshing = true
                    announcementViewModel.screenState.value.currentPage = 1
                    announcementViewModel.initAnnouncement()
                },
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        // Pass the SwipeRefreshState + trigger through
                        state = state,
                        refreshTriggerDistance = trigger,
                        // Enable the scale animation
                        scale = true,
                        // Change the color and shape
                        backgroundColor = androidx.compose.material.MaterialTheme.colors.primary.copy(
                            alpha = 0.08f
                        ),
                        shape = MaterialTheme.shapes.small,
                    )
                }
            ) {
                InfiniteAnnouncementList(
                    navController = navController,
                    userViewModel = userViewModel,
                    searchFormViewModel = searchFormViewModel,
                    announcementViewModel = announcementViewModel,
                    paddingValues = PaddingValues(
                        top = 0.dp,
                        bottom = innerPadding.calculateBottomPadding() + 100.dp
                    ),
                    country = country,
                    listState = listState,
                    listItems = remember { screenState.announcementList }
                )
            }

            // cette instruction permet de réactivé le reflesh
            LaunchedEffect(isRefreshing) {
                if (isRefreshing) {
                    delay(1000L)
                    isRefreshing = false
                }
            }

            if (screenState.isNetworkError) {
                announcementViewModel.onEvent(AnnouncementEvent.IsNetworkError)
            } else if (!screenState.isNetworkConnected) {
                announcementViewModel.onEvent(AnnouncementEvent.IsNetworkConnected)
            }

            LaunchedEffect(key1 = true) {
                announcementViewModel.uiEventFlow.collectLatest { event ->
                    when (event) {
                        is UIEvent.ShowMessage -> {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = event.message
                            )
                        }
                        else -> {}
                    }
                }
            }

        })
}