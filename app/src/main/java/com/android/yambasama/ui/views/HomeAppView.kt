package com.android.yambasama.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.*
import androidx.compose.animation.slideInHorizontally
import androidx.compose.ui.graphics.Color
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
import com.android.yambasama.ui.views.bottomnavigationviews.AddAnnouncementView
import com.android.yambasama.ui.views.model.BottomNavigationItem
import com.android.yambasama.ui.views.model.Route
import kotlinx.coroutines.delay
import androidx.compose.material.Scaffold
import androidx.compose.foundation.background
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.Brush
import com.android.yambasama.presentation.viewModel.token.TokenDataStoreViewModel

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

    userViewModel.onEvent(AuthEvent.GetSavedToken)
    //We test is the token exist
    if (screenState.tokenRoom.isNotEmpty()) {
        userViewModel.onEvent(AuthEvent.GetSavedUserByToken)
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
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .aspectRatio(1f)
                                                    .graphicsLayer {
                                                        compositingStrategy = CompositingStrategy.Offscreen
                                                    }
                                                    .drawWithCache {
                                                        val path = Path()
                                                        path.addOval(
                                                            Rect(
                                                                topLeft = Offset.Zero,
                                                                bottomRight = Offset(size.width,size.height)
                                                            )
                                                        )
                                                        onDrawWithContent {
                                                            clipPath(path = path) {
                                                                // this draws the actual image - if you don't call drawContent, it wont
                                                                // render anything
                                                                this@onDrawWithContent.drawContent()
                                                            }
                                                            val dotSize = size.width / 8f
                                                            // Clip a white border for the content
                                                            drawCircle(
                                                                Color.Black,
                                                                radius = dotSize,
                                                                center = Offset(
                                                                    x = size.width - dotSize,
                                                                    y = size.height - dotSize
                                                                ),
                                                                blendMode = BlendMode.Clear
                                                            )
                                                            //draw the red circle indication
                                                            drawCircle(
                                                                Color(0xFF4CAF50),
                                                                radius = dotSize * 0.8f,
                                                                center = Offset(
                                                                    x = size.width - dotSize,
                                                                    y = size.height - dotSize
                                                                )
                                                            )
                                                        }
                                                    }
                                                    .clickable {
                                                        navController.navigate(Route.accountView)
                                                    },
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
                                                    .size(40.dp)
                                                    .aspectRatio(1f)
                                                    .graphicsLayer {
                                                        compositingStrategy = CompositingStrategy.Offscreen
                                                    }
                                                    .clickable {
                                                        navController.navigate(Route.accountView)
                                                    }
                                                    .clip(RoundedCornerShape(corner = CornerSize(20.dp)))
                                                    .drawWithCache {
                                                        val path = Path()
                                                        path.addOval(
                                                            Rect(
                                                                topLeft = Offset.Zero,
                                                                bottomRight = Offset(size.width,size.height)
                                                            )
                                                        )
                                                        onDrawWithContent {
                                                            clipPath(path = path) {
                                                                // this draws the actual image - if you don't call drawContent, it wont
                                                                // render anything
                                                                this@onDrawWithContent.drawContent()
                                                            }
                                                            val dotSize = size.width / 8f
                                                            // Clip a white border for the content
                                                            drawCircle(
                                                                Color.Black,
                                                                radius = dotSize,
                                                                center = Offset(
                                                                    x = size.width - dotSize,
                                                                    y = size.height - dotSize
                                                                ),
                                                                blendMode = BlendMode.Clear
                                                            )
                                                            //draw the red circle indication
                                                            drawCircle(
                                                                Color(0xFFEF5350),
                                                                radius = dotSize * 0.8f,
                                                                center = Offset(
                                                                    x = size.width - dotSize,
                                                                    y = size.height - dotSize
                                                                )
                                                            )
                                                        }
                                                    }
                                                ,
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
        //we upgrade our token and refresh token
        if (screenState.tokenRoom.isNotEmpty()) {
            //we delete our token
            tokenDataStoreViewModel.deleteToken()
            tokenDataStoreViewModel.deleteRefreshToken()
            //we save our token
            tokenDataStoreViewModel.saveToken(token = screenState.tokenRoom[0].token)
            tokenDataStoreViewModel.saveRefreshToken(refreshToken = screenState.tokenRoom[0].refreshToken)
        }
    }
}

