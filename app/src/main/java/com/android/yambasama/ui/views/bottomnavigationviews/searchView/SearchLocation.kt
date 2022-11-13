package com.android.yambasama.ui.views.bottomnavigationviews.searchView

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay


@ExperimentalMaterial3Api
@Composable
fun SearchLocation(navController: NavHostController) {
    var visibleSearch by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val context = LocalContext.current
    //val locationList = mutableStateListOf<Location>()
    //val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    /*locationList.add(Location("Aeroport Maya Maya (Brazzaville)"))
    locationList.add(Location("Aeroport Maya Maya (Brazzaville)"))
    locationList.add(Location("Aeroport Maya Maya (Brazzaville)"))
    locationList.add(Location("Aeroport Maya Maya (Brazzaville)"))
    locationList.add(Location("Aeroport Maya Maya (Brazzaville)"))
    locationList.add(Location("Aeroport Maya Maya (Brazzaville)"))
    locationList.add(Location("Aeroport Maya Maya (Brazzaville)"))
    locationList.add(Location("Aeroport Maya Maya (Brazzaville)"))
    locationList.add(Location("Aeroport Maya Maya (Brazzaville)"))*/


    Scaffold(topBar = {
        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = {
                }) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            actions =  {},
            scrollBehavior = scrollBehavior
        )
    },
        content= {innerPadding ->
            LazyColumn(contentPadding = innerPadding, state = listState) {
                items(1000) {location ->
                    SearchTownItem()
                }
            }
    })

    LaunchedEffect(true) {
        delay(3)
        visibleSearch = true
    }
}