package com.android.yambasama.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.*
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.android.yambasama.BuildConfig
import com.android.yambasama.R
import com.android.yambasama.presentation.viewModel.announcement.AnnouncementViewModel
import com.android.yambasama.presentation.viewModel.drop.DropViewModel
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModel
import com.android.yambasama.presentation.viewModel.user.UserViewModel
import com.android.yambasama.ui.UIEvent.Event.AuthEvent
import com.android.yambasama.ui.util.Util
import com.android.yambasama.ui.views.bottomnavigationviews.SearchView
import com.android.yambasama.ui.views.bottomnavigationviews.AddAdView
import com.android.yambasama.ui.views.model.BottomNavigationItem
import com.android.yambasama.ui.views.model.Route
import kotlinx.coroutines.delay
import androidx.compose.material.Scaffold

@Composable
@ExperimentalMaterial3Api
fun HomeApp(
    navController: NavHostController,
    dropViewModel: DropViewModel,
    userViewModel: UserViewModel,
    searchFormViewModel: SearchFormViewModel,
    announcementViewModel: AnnouncementViewModel,
    visibleCurrentForm: MutableState<Boolean>,
    visibleNextForm: MutableState<Boolean>
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val screenState = userViewModel.screenState.value
    var switch by rememberSaveable { mutableStateOf(true) }
    var selectedItem by remember { mutableStateOf(0) }
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

    userViewModel.onEvent(AuthEvent.GetSavedToken)
    //We test is the token exist
    if (screenState.tokenRoom.isNotEmpty()) {
        if (screenState.tokenRoom[0] !== null) {
            userViewModel.onEvent(AuthEvent.GetSavedUserByToken)
        }
    }
    var visibleSearch by remember { mutableStateOf(false) }

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                backgroundColor = MaterialTheme.colorScheme.background,
                topBar = {
                    TopAppBar(
                        navigationIcon = {

                        },
                        actions = {

                            var expanded by remember { mutableStateOf(false) }
                            //We add our badges
                            BadgedBox(badge = {
                                Badge {
                                    val badgeNumber = "8"
                                    Text(
                                        badgeNumber,
                                        modifier = Modifier.semantics {
                                            contentDescription = "$badgeNumber new notifications"
                                        }
                                    )
                                }
                            }) {

                                Icon(
                                    imageVector = Icons.Outlined.Notifications,
                                    contentDescription = "Localized description",
                                    modifier = Modifier.clickable {
                                        navController.navigate(Route.notificationListView)
                                    }
                                )
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
                                    text = {
                                        Text(
                                            text = stringResource(id = R.string.account),
                                            maxLines = 1
                                        )
                                    },
                                    onClick = {
                                        navController.navigate(Route.accountView)
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Outlined.AccountCircle,
                                            contentDescription = null
                                        )
                                    })

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = stringResource(id = R.string.settings),
                                            maxLines = 1
                                        )
                                    },
                                    onClick = { /* Handle edit! */ },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Outlined.Settings,
                                            contentDescription = null
                                        )
                                    })

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = stringResource(id = R.string.contact_us),
                                            maxLines = 1
                                        )
                                    },
                                    onClick = { /* Handle edit! */ },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Outlined.ContactPage,
                                            contentDescription = null
                                        )
                                    }
                                )

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = stringResource(id = R.string.guest_friend),
                                            maxLines = 1
                                        )
                                    },
                                    onClick = { /* Handle edit! */ },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Outlined.PeopleOutline,
                                            contentDescription = null
                                        )
                                    })

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = stringResource(id = R.string.about),
                                            maxLines = 1
                                        )
                                    },
                                    onClick = { /* Handle edit! */ },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Outlined.HelpOutline,
                                            contentDescription = null
                                        )
                                    })

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            stringResource(id = R.string.logout)
                                        )
                                    },
                                    onClick = {
                                        /* Handle settings! */
                                        dropViewModel.deleteAll()
                                        userViewModel.onEvent(AuthEvent.InitUserState)
                                        navController.navigate(Route.loginView)
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Outlined.Logout,
                                            contentDescription = null
                                        )
                                    })
                            }
                        },
                        scrollBehavior = scrollBehavior,
                        title = {
                            if (screenState.userRoom.isNotEmpty() && screenState.userRoom[0] !== null) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    AnimatedVisibility(
                                        visible = visibleSearch,
                                        enter = slideInHorizontally(
                                            animationSpec = tween(
                                                durationMillis = 200
                                            )
                                        ) { fullWidth ->
                                            // Offsets the content by 1/3 of its width to the left, and slide towards right
                                            // Overwrites the default animation with tween for this slide animation.
                                            -fullWidth / 3
                                        } + fadeIn(
                                            // Overwrites the default animation with tween
                                            animationSpec = tween(durationMillis = 200)
                                        ),
                                        exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) {
                                            // Overwrites the ending position of the slide-out to 200 (pixels) to the right
                                            200
                                        } + fadeOut()
                                    ) {

                                        if (screenState.userRoom[0].imageUrl?.length == 0) {
                                            Image(
                                                painter = painterResource(id = R.drawable.ic_profile_colorier),
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .clip(RoundedCornerShape(corner = CornerSize(20.dp)))
                                                    .clickable {
                                                        navController.navigate(Route.accountView)
                                                    },
                                                contentScale = ContentScale.Crop,
                                                contentDescription = "Profile picture description"
                                            )
                                        } else {
                                            Image(
                                                painter = rememberAsyncImagePainter(
                                                    model = "${BuildConfig.BASE_URL_DEV}/images/${screenState.userRoom[0].imageUrl}",
                                                    placeholder = painterResource(id = R.drawable.ic_profile_colorier),
                                                    error = painterResource(id = R.drawable.ic_profile_colorier),
                                                ),
                                                modifier = Modifier
                                                    .height(40.dp)
                                                    .width(40.dp)
                                                    .clickable {
                                                        navController.navigate(Route.accountView)
                                                    }
                                                    .clip(RoundedCornerShape(corner = CornerSize(20.dp))),
                                                contentDescription = "Profile picture description",
                                                contentScale = ContentScale.Crop,
                                            )
                                        }
                                    }

                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(start = 10.dp),
                                            text = stringResource(R.string.app_name),
                                            color = MaterialTheme.colorScheme.primary
                                        )

                                        Text(
                                            modifier = Modifier.padding(start = 10.dp),
                                            fontSize = 15.sp,
                                            text = "${screenState.userRoom[0].firstName} ${screenState.userRoom[0].lastName}",
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                }
                            }
                        }
                    )
                }) { innerPadding ->


                    if (switch) {
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
                            AddAdView(
                                util = util,
                                visibleCurrentForm = visibleCurrentForm,
                                visiblePreviousForm = visibleNextForm
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
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            switch = index != 1
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

