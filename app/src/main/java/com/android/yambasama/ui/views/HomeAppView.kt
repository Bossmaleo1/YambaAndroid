package com.android.yambasama.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.yambasama.R
import com.android.yambasama.presentation.viewModel.user.UserViewModel
import com.android.yambasama.ui.views.bottomnavigationviews.SearchView
import com.android.yambasama.ui.views.bottomnavigationviews.AddAdView
import com.android.yambasama.ui.views.model.BottomNavigationItem
import com.android.yambasama.ui.views.model.Route
import kotlinx.coroutines.CoroutineScope

@Composable
@ExperimentalMaterial3Api
fun HomeApp(
    navController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    context: Any,
    userViewModel: UserViewModel
) {
    val navController2 = rememberNavController()
    val navBackStackEntry by navController2.currentBackStackEntryAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val listState = rememberLazyListState()
    /*val currentRoute =
        navBackStackEntry?.destination?.route ?: WazzabyDrawerDestinations.HOME_ROUTE*/
    var switch by rememberSaveable { mutableStateOf(true) }
    var selectedItem by remember { mutableStateOf(0) }
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
    Scaffold(topBar = {
        //DrawerAppBar(scope, drawerState, "DiaspoPay",viewItem, context)
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                SmallTopAppBar(
                    navigationIcon = {

                    },
                    actions = {

                        var expanded by remember { mutableStateOf(false) }
                        IconButton(onClick = { /* doSomething() */ }) {
                            BadgedBox(badge = { Badge { Text("8") } }) {
                                Icon(
                                    imageVector = Icons.Filled.Notifications,
                                    contentDescription = "Localized description"
                                )
                            }
                        }

                        IconButton(onClick = {
                            expanded = true
                        }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "Localized description"
                            )
                        }

                        //we create our Dropdown Menu Item
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("xxxx") },
                                onClick = { /* Handle edit! */ },
                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.AccountCircle,
                                        contentDescription = null
                                    )
                                })
                            DropdownMenuItem(
                                text = { Text("xxxx") },
                                onClick = { /* Handle settings! */ },
                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.History,
                                        contentDescription = null
                                    )
                                })
                        }
                    },
                    scrollBehavior = scrollBehavior,
                    title = { Text("Yamba") }
                )
            }) { innerPadding ->

            if (switch) {
                Column(Modifier.padding(top = 100.dp, bottom = 0.dp, start = 0.dp, end = 0.dp)) {
                    SearchView(navController)
                }
            } else {
                AddAdView()
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
                        label = { Text(
                            remember {item.title}) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            switch = index != 1
                        }
                    )
                }
            }
        }, floatingActionButton = {
            /*if (switch) {
                ExtendedFloatingActionButton(
                    icon = { Icon(Icons.Filled.EuroSymbol, "") },
                    text = {
                        Text(
                            text = "Envoyer de l'argent",
                            style = MaterialTheme.typography.titleSmall
                        )
                    },
                    onClick = {/*do something*/ },
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                )
            }*/
        }, content = { innerPadding -> })

}