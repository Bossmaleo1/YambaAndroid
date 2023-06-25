package com.android.yambasama.ui.views.bottomnavigationviews.annoucement.announcementDetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.yambasama.R
import com.android.yambasama.presentation.viewModel.announcement.AnnouncementViewModel
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModel
import com.android.yambasama.ui.util.Util
import com.android.yambasama.ui.views.bottomnavigationviews.annoucement.announcementDetails.announcementlist.getOurUserImage
import com.android.yambasama.ui.views.model.Route
import kotlinx.coroutines.delay
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.ui.Alignment
import com.android.yambasama.presentation.viewModel.user.UserViewModel
import com.android.yambasama.ui.UIEvent.Event.AnnouncementEvent
import com.android.yambasama.ui.UIEvent.UIEvent
import com.android.yambasama.ui.views.shimmer.AnnouncementDetailsShimmer
import com.android.yambasama.ui.views.shimmer.AnnouncementShimmer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterial3Api
@Composable
fun AnnouncementDetails(
    navController: NavHostController,
    announcementViewModel: AnnouncementViewModel,
    searchFormViewModel: SearchFormViewModel,
    userViewModel: UserViewModel
) {
    val util = Util()
    val scaffoldState = rememberScaffoldState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val screenState = announcementViewModel.screenState.value
    var visibleImage by remember { mutableStateOf(false) }
    val userName by rememberSaveable { mutableStateOf("${screenState.announcement[0].user.firstName} ${screenState.announcement[0].user.lastName}") }
    val isDark = isSystemInDarkTheme()
    val departureDate by rememberSaveable { mutableStateOf(util.getDateTimeFormatter(screenState.announcement[0].arrivingTime)) }
    val postDateTime by rememberSaveable { mutableStateOf(util.getDateFormatter(screenState.announcement[0].published)) }
    val screenAnnouncementState = announcementViewModel.screenAnnouncementState.value
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(value = false) }
    val screenStateUser = userViewModel.screenState.value

    fun refresh() = refreshScope.launch {
        refreshing = true
        announcementViewModel.screenAnnouncementState.value.refreshing = true
        announcementViewModel.onEvent(
            AnnouncementEvent.AnnouncementDetails(
                token = screenStateUser.tokenRoom[0].token,
                id = screenState.announcement[0].id
            )
        )
    }

    if (!announcementViewModel.screenAnnouncementState.value.refreshing) {
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)

    LaunchedEffect(key1 = true) {
        announcementViewModel.onEvent(
            AnnouncementEvent.AnnouncementDetails(
                token = screenStateUser.tokenRoom[0].token,
                id = screenState.announcement[0].id
            )
        )
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            androidx.compose.material3.Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = {
                                navController.navigateUp()
                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.ArrowBack,
                                    contentDescription = "",
                                    //tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        actions = {
                            IconButton(
                                modifier = Modifier,
                                onClick = {
                                    navController.navigate(Route.paymentView)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Done,
                                    contentDescription = "Localized description",
                                    tint = if (!isDark) {
                                        colorResource(R.color.black40)
                                    } else {
                                        Color.White
                                    }
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior,
                        title = {
                            Text(
                                text = stringResource(R.string.announcement_details),
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
                    Box(Modifier.pullRefresh(state)) {
                        LazyColumn(
                            contentPadding = PaddingValues(
                                top = 0.dp,
                                bottom = innerPadding.calculateBottomPadding() + 100.dp
                            ),
                            state = rememberLazyListState()
                        ) {

                            if (screenAnnouncementState.isLoad) {
                                items(count = 1) {
                                    Column(
                                        modifier = Modifier.padding(top = 80.dp)
                                    ) {
                                        AnnouncementDetailsShimmer(1)
                                    }
                                }
                            }

                            if (!refreshing && !screenAnnouncementState.isLoad) {
                                items(count = 1) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(innerPadding),
                                        backgroundColor = MaterialTheme.colorScheme.background,
                                    ) {
                                        Column {
                                            Row {
                                                Divider(
                                                    color = MaterialTheme.colorScheme.background,
                                                    modifier = Modifier.padding(
                                                        bottom = 2.dp,
                                                        top = 2.dp
                                                    )
                                                )
                                            }
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.Start
                                            ) {
                                                AnimatedVisibility(
                                                    visible = visibleImage,
                                                    enter = fadeIn(
                                                        // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                                                        initialAlpha = 0.4f
                                                    ),
                                                    exit = fadeOut(
                                                        // Overwrites the default animation with tween
                                                        animationSpec = tween(durationMillis = 250)
                                                    )
                                                ) {
                                                    // Content that needs to appear/disappear goes here:
                                                    Image(
                                                        painter = getOurUserImage(screenState.announcement[0]),
                                                        contentDescription = "Profile picture description",
                                                        modifier = Modifier
                                                            .padding(4.dp)
                                                            .size(50.dp)
                                                            .clip(
                                                                RoundedCornerShape(
                                                                    corner = CornerSize(
                                                                        25.dp
                                                                    )
                                                                )
                                                            )
                                                            .clickable {
                                                                navController.navigate(Route.accountDetailView)
                                                            },
                                                        contentScale = ContentScale.Crop
                                                    )
                                                }

                                                Column(modifier = Modifier.padding(4.dp)) {
                                                    Text(
                                                        text = userName,
                                                        style = MaterialTheme.typography.titleMedium,
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                    Row {
                                                        Image(
                                                            imageVector = Icons.Outlined.AccessTime,
                                                            contentDescription = "",
                                                            contentScale = ContentScale.Crop,
                                                            modifier = Modifier
                                                                .height(18.dp)
                                                                .width(18.dp),
                                                            colorFilter = ColorFilter.tint(
                                                                color = MaterialTheme.colorScheme.primary
                                                            )
                                                        )

                                                        Text(
                                                            text = postDateTime,
                                                            modifier = Modifier.padding(
                                                                4.dp,
                                                                0.dp,
                                                                0.dp,
                                                                0.dp
                                                            ),
                                                            style = MaterialTheme.typography.titleSmall,
                                                            color = MaterialTheme.colorScheme.primary
                                                        )
                                                    }
                                                    Divider(
                                                        color = MaterialTheme.colorScheme.primary,
                                                        modifier = Modifier
                                                            .padding(top = 10.dp)
                                                            .fillMaxWidth()
                                                            .height(0.20.dp),
                                                    )
                                                }

                                            }

                                            Row {
                                                Divider(
                                                    color = MaterialTheme.colorScheme.background,
                                                    modifier = Modifier.padding(
                                                        bottom = 10.dp,
                                                        top = 5.dp
                                                    )
                                                )
                                            }

                                            Row(
                                                modifier = Modifier
                                                    .padding(9.dp)
                                                    .animateContentSize(),
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Image(
                                                    imageVector = Icons.Outlined.FlightTakeoff,
                                                    contentDescription = "",
                                                    contentScale = ContentScale.Crop,
                                                    colorFilter = ColorFilter.tint(
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                )

                                                Text(
                                                    text = "${searchFormViewModel.screenState.value.addressDeparture?.townName} ( ${
                                                        util.getCountry(
                                                            searchFormViewModel.screenState.value.addressDeparture!!.code
                                                        )
                                                    } ( ${searchFormViewModel.screenState.value.addressDeparture?.airportName}, ${searchFormViewModel.screenState.value.addressDeparture?.airportCode} ))",
                                                    modifier = Modifier.padding(4.dp),
                                                    style = MaterialTheme.typography.titleSmall,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }

                                            Row(
                                                modifier = Modifier
                                                    .padding(9.dp)
                                                    .animateContentSize(),
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Image(
                                                    imageVector = Icons.Outlined.FlightLand,
                                                    contentDescription = "",
                                                    contentScale = ContentScale.Crop,
                                                    colorFilter = ColorFilter.tint(
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                )

                                                Text(
                                                    text = "${searchFormViewModel.screenState.value.addressDestination?.townName} ( ${
                                                        util.getCountry(
                                                            searchFormViewModel.screenState.value.addressDestination!!.code
                                                        )
                                                    } ( ${searchFormViewModel.screenState.value.addressDestination?.airportName}, ${searchFormViewModel.screenState.value.addressDestination?.airportCode} ))",
                                                    modifier = Modifier.padding(4.dp),
                                                    style = MaterialTheme.typography.titleSmall,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }

                                            Row(
                                                modifier = Modifier
                                                    .padding(9.dp)
                                                    .animateContentSize(),
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Image(
                                                    imageVector = Icons.Outlined.EventNote,
                                                    contentDescription = "",
                                                    contentScale = ContentScale.Crop,
                                                    colorFilter = ColorFilter.tint(
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                )

                                                Text(
                                                    text = "${stringResource(R.string.available_kilo_number)} : ${departureDate}",
                                                    modifier = Modifier.padding(
                                                        4.dp,
                                                        0.dp,
                                                        0.dp,
                                                        0.dp
                                                    ),
                                                    style = MaterialTheme.typography.titleSmall,
                                                    textAlign = TextAlign.Center,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }


                                            Row(
                                                modifier = Modifier
                                                    .padding(9.dp)
                                                    .animateContentSize(),
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Image(
                                                    imageVector = Icons.Outlined.EventNote,
                                                    contentDescription = "",
                                                    contentScale = ContentScale.Crop,
                                                    colorFilter = ColorFilter.tint(
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                )

                                                Text(
                                                    text = "${stringResource(R.string.arrival_date)} : ${
                                                        screenAnnouncementState.announcementDetails?.departureTime?.let {
                                                            util.getDateTimeFormatter(
                                                                it
                                                            )
                                                        }
                                                    }",
                                                    modifier = Modifier.padding(
                                                        4.dp,
                                                        0.dp,
                                                        0.dp,
                                                        0.dp
                                                    ),
                                                    style = MaterialTheme.typography.titleSmall,
                                                    textAlign = TextAlign.Center,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }

                                            Row(
                                                modifier = Modifier
                                                    .padding(9.dp)
                                                    .animateContentSize(),
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Image(
                                                    imageVector = Icons.Outlined.EuroSymbol,
                                                    contentDescription = "",
                                                    contentScale = ContentScale.Crop,
                                                    colorFilter = ColorFilter.tint(
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                )

                                                Text(
                                                    text = "${stringResource(R.string.price)} : ${screenState.announcement[0].price} €/Kg",
                                                    modifier = Modifier.padding(
                                                        4.dp,
                                                        0.dp,
                                                        0.dp,
                                                        0.dp
                                                    ),
                                                    style = MaterialTheme.typography.titleSmall,
                                                    textAlign = TextAlign.Center,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }

                                            Row(
                                                modifier = Modifier
                                                    .padding(9.dp)
                                                    .animateContentSize(),
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.weight_fill0_wght400_grad0_opsz48),
                                                    contentDescription = "",
                                                    modifier = Modifier
                                                        .height(24.dp)
                                                        .width(24.dp),
                                                    contentScale = ContentScale.Crop,
                                                    colorFilter = ColorFilter.tint(
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                )

                                                Text(
                                                    text = "${stringResource(R.string.available_kilo_number)} : ${
                                                        screenAnnouncementState.announcementDetails?.numberOfKgs?.let {
                                                            util.getNumberOfKg(
                                                                it
                                                            )
                                                        }
                                                    } Kg (max)",
                                                    modifier = Modifier.padding(
                                                        4.dp,
                                                        0.dp,
                                                        0.dp,
                                                        0.dp
                                                    ),
                                                    style = MaterialTheme.typography.titleSmall,
                                                    textAlign = TextAlign.Center,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }

                                            Row(
                                                modifier = Modifier
                                                    .padding(9.dp)
                                                    .animateContentSize(),
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Image(
                                                    imageVector = Icons.Outlined.LocationOn,
                                                    contentDescription = "",
                                                    contentScale = ContentScale.Crop,
                                                    colorFilter = ColorFilter.tint(
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                )

                                                Text(
                                                    text = "${stringResource(R.string.departure_appointment)} : ${screenAnnouncementState.announcementDetails?.meetingPlaces1}",
                                                    modifier = Modifier.padding(
                                                        4.dp,
                                                        0.dp,
                                                        0.dp,
                                                        0.dp
                                                    ),
                                                    style = MaterialTheme.typography.titleSmall,
                                                    textAlign = TextAlign.Center,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }

                                            Row(
                                                modifier = Modifier
                                                    .padding(9.dp)
                                                    .animateContentSize(),
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Image(
                                                    imageVector = Icons.Outlined.LocationOn,
                                                    contentDescription = "",
                                                    contentScale = ContentScale.Crop,
                                                    colorFilter = ColorFilter.tint(
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                )

                                                Text(
                                                    text = "${stringResource(R.string.arrival_meeting)} : ${screenAnnouncementState.announcementDetails?.meetingPlaces2}",
                                                    modifier = Modifier.padding(
                                                        4.dp,
                                                        0.dp,
                                                        0.dp,
                                                        0.dp
                                                    ),
                                                    style = MaterialTheme.typography.titleSmall,
                                                    textAlign = TextAlign.Center,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }

                                            Divider(
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier
                                                    .padding(top = 10.dp)
                                                    .fillMaxWidth()
                                                    .height(0.20.dp),
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))

                        if (screenAnnouncementState.isNetworkError) {
                            announcementViewModel.onEvent(AnnouncementEvent.IsNetworkError)
                        } else if (!screenAnnouncementState.isNetworkConnected) {
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

                        LaunchedEffect(true) {
                            delay(3)
                            visibleImage = true
                        }
                    }
                }
            )

        },
        content = { innerPadding ->

        })
}