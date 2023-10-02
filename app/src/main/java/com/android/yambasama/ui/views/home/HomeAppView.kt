package com.android.yambasama.ui.views.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.yambasama.R
import com.android.yambasama.presentation.viewModel.announcement.AnnouncementViewModel
import com.android.yambasama.presentation.viewModel.drop.DropViewModel
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModel
import com.android.yambasama.presentation.viewModel.user.UserViewModel
import com.android.yambasama.ui.UIEvent.Event.AuthEvent
import com.android.yambasama.ui.util.Util
import com.android.yambasama.ui.views.bottomnavigationviews.SearchView
import com.android.yambasama.ui.views.bottomnavigationviews.AddAnnouncementView
import com.android.yambasama.ui.views.model.BottomNavigationItem
import com.android.yambasama.ui.views.model.Route
import kotlinx.coroutines.delay
import androidx.compose.material.Scaffold
import androidx.compose.runtime.saveable.rememberSaveable
import com.android.yambasama.presentation.viewModel.token.TokenDataStoreViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@Composable
@ExperimentalMaterial3Api
fun HomeApp(
    navController: NavHostController,
    dropViewModel: DropViewModel,
    userViewModel: UserViewModel,
    searchFormViewModel: SearchFormViewModel,
    announcementViewModel: AnnouncementViewModel,
    visibleCurrentForm: MutableState<Boolean>,
    visibleNextForm: MutableState<Boolean>,
    switch: MutableState<Boolean>,
    selectedItem: MutableState<Int>,
    tokenDataStoreViewModel: TokenDataStoreViewModel
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val screenState = userViewModel.screenState.value
    val util = Util()
    val items = listOf(
        BottomNavigationItem(
            Icons.Outlined.Search,
            stringResource(R.string.re_search),
            Route.historyTabView
        ),
        BottomNavigationItem(
            Icons.Outlined.Add,
            stringResource(R.string.add_an_ad),
            Route.homeTabView
        )
    )


    var tokenValue by rememberSaveable { mutableStateOf("") }
    var userId by rememberSaveable { mutableStateOf("") }

    //we get our token in Data Store
    runBlocking {
        tokenValue = tokenDataStoreViewModel.getToken().first().toString()
        userId = tokenDataStoreViewModel.getUserId().first().toString()
    }

    if (screenState.userRoom === null && userId !== null) {
        userViewModel.onEvent(AuthEvent.GetSavedUser(userId = userId.toInt()))
    }

    var visibleSearch by remember { mutableStateOf(false) }

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                backgroundColor = MaterialTheme.colorScheme.background,
                topBar = {
                    screenState.userRoom?.let {
                        HomeToolBar(
                            navController = navController,
                            dropViewModel = dropViewModel,
                            userViewModel = userViewModel,
                            userRoom = it,
                            visibleSearch = visibleSearch
                        )
                    }
                }) { innerPadding ->


                    if (switch.value) {
                        Column(
                            Modifier.padding(
                                top = 100.dp
                            )
                        ) {
                            SearchView(
                                navController = navController,
                                searchFormViewModel = searchFormViewModel,
                                util = util,
                                announcementViewModel = announcementViewModel
                            )
                        }
                    } else {
                        Column(
                            Modifier.padding(
                                top = 20.dp
                            )
                        ) {
                            AddAnnouncementView(
                                util = util,
                                navController = navController,
                                visibleCurrentForm = visibleCurrentForm,
                                visiblePreviousForm = visibleNextForm,
                                searchFormViewModel = searchFormViewModel,
                                announcementViewModel = announcementViewModel,
                                userViewModel = userViewModel
                            )
                        }
                    }


            }
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                item.id,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                remember { item.title })
                        },
                        selected = selectedItem.value == index,
                        onClick = {
                            selectedItem.value = index
                            switch.value = index != 1
                        }
                    )
                }
            }
        }, floatingActionButton = {}, content = {})

    LaunchedEffect(true) {
        delay(3)
        visibleSearch = true
    }
}

