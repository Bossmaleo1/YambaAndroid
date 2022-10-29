package com.android.yambasama.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import com.android.yambasama.ui.views.model.Route

@Composable
@ExperimentalMaterial3Api
fun SearchResult(
    navController: NavHostController,
    context: Any
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val listState = rememberLazyListState()

    Scaffold(topBar = {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            // Set up the ActionBar to stay in sync with the NavController
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    actions = {},
                    scrollBehavior = scrollBehavior,
                    title = {
                        Text("Annonces")
                    }
                )
            }) { innerPadding ->
            LazyColumn(contentPadding = innerPadding, state = listState) {
                items(count = 2000) {
                    SearchResultCell()
                }

                /*items(count = 3) {
                    repeat(3) {
                        PublicMessageShimmer()
                    }
                }*/
            }
        }
    },
        bottomBar = {},
        floatingActionButton = {},
        content = { innerPadding ->

            LazyColumn(contentPadding = innerPadding, state = listState) {
                items(count = 2000) {
                    SearchResultCell()
                }

                /*items(count = 3) {
                    repeat(3) {
                        PublicMessageShimmer()
                    }
                }*/
            }

        })
}