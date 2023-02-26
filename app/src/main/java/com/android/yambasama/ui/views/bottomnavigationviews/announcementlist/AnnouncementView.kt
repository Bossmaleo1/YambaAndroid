package com.android.yambasama.ui.views.bottomnavigationviews.announcementlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.yambasama.R
import com.android.yambasama.presentation.viewModel.announcement.AnnouncementViewModel
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModel
import com.android.yambasama.presentation.viewModel.user.UserViewModel
import com.android.yambasama.ui.UIEvent.Event.AddressEvent
import com.android.yambasama.ui.UIEvent.Event.AnnouncementEvent
import com.android.yambasama.ui.UIEvent.UIEvent
import com.android.yambasama.ui.views.bottomnavigationviews.searchview.SearchTownItem
import com.android.yambasama.ui.views.shimmer.AddressShimmer
import com.android.yambasama.ui.views.shimmer.AnnouncementShimmer
import com.android.yambasama.ui.views.viewsError.networkError
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterial3Api
@Composable
fun AnnouncementView(
    navController: NavHostController,
    userViewModel: UserViewModel,
    searchFormViewModel: SearchFormViewModel,
    announcementViewModel: AnnouncementViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val screenState = announcementViewModel.screenState.value
    val screenStateUser = userViewModel.screenState.value
    val screenStateSearchForm = searchFormViewModel.screenState.value

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    
                },
                scrollBehavior = scrollBehavior,
                title = {
                    Text(text = stringResource(R.string.announcements))
                }
            )
        },
        content = { innerPadding ->

            LazyColumn(
                contentPadding = PaddingValues(
                    top = 0.dp,
                    bottom = innerPadding.calculateBottomPadding() + 100.dp
                ),
                state = listState
            ) {

                items(screenState.announcementList) { announcement ->
                    AnnouncementItem(
                        navController = navController,
                        announcement = announcement,
                        searchFormViewModel = searchFormViewModel
                    )
                }

                if (screenState.isLoad) {
                    items(count = 1) {
                        AnnouncementShimmer()
                    }
                }

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