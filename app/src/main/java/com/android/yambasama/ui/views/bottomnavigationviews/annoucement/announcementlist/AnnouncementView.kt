package com.android.yambasama.ui.views.bottomnavigationviews.annoucement.announcementDetails.announcementlist

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
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
    val isDark = isSystemInDarkTheme()

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    val context = LocalContext.current

    fun refresh() = refreshScope.launch {
        refreshing = true
        announcementViewModel.screenState.value.refreshing = true
        announcementViewModel.screenState.value.currentPage = 1
        announcementViewModel.initAnnouncement()
        searchFormViewModel.screenState.value.addressDeparture?.id?.let {
            searchFormViewModel.screenState.value.addressDestination?.id?.let { it1 ->
                searchFormViewModel.screenState.value.arrivingTimeAfter?.let { it2 ->
                    searchFormViewModel.screenState.value.arrivingTimeBefore?.let { it3 ->
                        AnnouncementEvent.AnnouncementInt(
                            app = context,
                            destinationAddressId = it1,
                            departureAddressId = it,
                            arrivingTimeAfter = it2,
                            arrivingTimeBefore = it3,
                            refreshing = refreshing
                        )
                    }
                }
            }
        }?.let {
            announcementViewModel.onEvent(
                it
            )
        }
    }

    if(!announcementViewModel.screenState.value.refreshing) {
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)

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
                if (screenState.currentPage == 1 && !refreshing && screenState.announcementList.size == 0) {
                    searchFormViewModel.screenState.value.addressDeparture?.id?.let {
                        searchFormViewModel.screenState.value.addressDestination?.id?.let { it1 ->
                            searchFormViewModel.screenState.value.arrivingTimeAfter?.let { it2 ->
                                searchFormViewModel.screenState.value.arrivingTimeBefore?.let { it3 ->
                                    AnnouncementEvent.AnnouncementInt(
                                        app = context,
                                        destinationAddressId = it1,
                                        departureAddressId = it,
                                        arrivingTimeAfter = it2,
                                        arrivingTimeBefore = it3,
                                        refreshing = refreshing
                                    )
                                }
                            }
                        }
                    }?.let {
                        announcementViewModel.onEvent(
                            it
                        )
                    }

                }

            }

            Box(Modifier.pullRefresh(state)) {
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
                    listItems = screenState.announcementList,
                    refreshing = refreshing
                )

                PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
            }

            if (screenState.isNetworkError) {
                announcementViewModel.onEvent(AnnouncementEvent.IsNetworkError(context.getString(R.string.is_connect_error)))
            } else if (!screenState.isNetworkConnected) {
                announcementViewModel.onEvent(AnnouncementEvent.IsNetworkConnected(context.getString(R.string.network_error)))
            } else if(screenState.isEmptyAnnouncement && !screenState.isLoad) {
                announcementViewModel.onEvent(AnnouncementEvent.IsEmptyAnnouncement(context.getString(R.string.empty_announcement_date)))
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