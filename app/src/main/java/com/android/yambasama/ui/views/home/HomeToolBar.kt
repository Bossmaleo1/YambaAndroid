package com.android.yambasama.ui.views.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.android.yambasama.data.model.dataLocal.UserRoom
import com.android.yambasama.presentation.viewModel.drop.DropViewModel
import com.android.yambasama.presentation.viewModel.user.UserViewModel

@Composable
@ExperimentalMaterial3Api
fun HomeToolBar(
    navController: NavHostController,
    dropViewModel: DropViewModel,
    userViewModel: UserViewModel,
    userRoom: UserRoom,
    visibleSearch: Boolean
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    TopAppBar(
        navigationIcon = {

        },
        actions = {
            Menus(
                navController = navController,
                dropViewModel = dropViewModel,
                userViewModel = userViewModel
            )
        },
        scrollBehavior = scrollBehavior,
        title = {
            userRoom?.let {
                HomeUserProfil(
                    userRoom = it,
                    navController = navController,
                    visibleSearch = visibleSearch
                )
            }
        }
    )
}