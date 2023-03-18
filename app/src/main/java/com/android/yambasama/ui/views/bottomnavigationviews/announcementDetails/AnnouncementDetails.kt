package com.android.yambasama.ui.views.bottomnavigationviews.announcementDetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonitorWeight
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
import com.android.yambasama.ui.views.bottomnavigationviews.announcementlist.getOurUserImage
import com.android.yambasama.ui.views.model.Route
import kotlinx.coroutines.delay

@ExperimentalMaterial3Api
@Composable
fun AnnouncementDetails(
    navController: NavHostController,
    announcementViewModel: AnnouncementViewModel,
    searchFormViewModel: SearchFormViewModel
) {
    val util = Util()
    val scaffoldState = rememberScaffoldState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val screenState = announcementViewModel.screenState.value
    var visibleImage by remember { mutableStateOf(false) }
    val userName by rememberSaveable { mutableStateOf("${screenState.announcement[0].user.firstName} ${screenState.announcement[0].user.lastName}") }
    val isDark = isSystemInDarkTheme()
    val destinationDate by rememberSaveable { mutableStateOf(util.getDateTimeFormatter(screenState.announcement[0].arrivingTime)) }
    val postDateTime by rememberSaveable { mutableStateOf(util.getDateFormatter(screenState.announcement[0].published)) }

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
                                onClick = { }
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
                                //color = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                },
                content = { innerPadding ->
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Row(
                            modifier = Modifier,
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
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .height(50.dp)
                                        .width(50.dp)
                                        .clip(RoundedCornerShape(corner = CornerSize(25.dp)))
                                )
                            }

                            Column(modifier = Modifier.padding(4.dp)) {
                                Text(
                                    text = userName,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = if (!isDark) {
                                        colorResource(R.color.black40)
                                    } else {
                                        Color.White
                                    }
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
                                            color = if (!isDark) {
                                                colorResource(R.color.black40)
                                            } else {
                                                Color.White
                                            }
                                        )
                                    )

                                    Text(
                                        text = postDateTime,
                                        modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
                                        style = MaterialTheme.typography.titleSmall,
                                        color = if (!isDark) {
                                            colorResource(R.color.black40)
                                        } else {
                                            Color.White
                                        }
                                    )
                                }
                            }

                        }


                        Row {
                            Divider(
                                color = if (!isDark) {
                                    Color.White
                                } else {
                                    Color.Black
                                },
                                modifier = Modifier.padding(bottom = 10.dp, top = 5.dp)
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
                                    color = if (!isDark) {
                                        colorResource(R.color.black40)
                                    } else {
                                        Color.White
                                    }
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
                                color = if (!isDark) {
                                    colorResource(R.color.black40)
                                } else {
                                    Color.White
                                }
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
                                    color = if (!isDark) {
                                        colorResource(R.color.black40)
                                    } else {
                                        Color.White
                                    }
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
                                color = if (!isDark) {
                                    colorResource(R.color.black40)
                                } else {
                                    Color.White
                                }
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
                                    color = if (!isDark) {
                                        colorResource(R.color.black40)
                                    } else {
                                        Color.White
                                    }
                                )
                            )

                            Text(
                                text = destinationDate,
                                modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
                                style = MaterialTheme.typography.titleSmall,
                                textAlign = TextAlign.Center,
                                color = if (!isDark) {
                                    colorResource(R.color.black40)
                                } else {
                                    Color.White
                                }
                            )
                        }

                        Row(
                            modifier = Modifier
                                .padding(9.dp)
                                .animateContentSize(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                imageVector = Icons.Outlined.Timer,
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.tint(
                                    color = if (!isDark) {
                                        colorResource(R.color.black40)
                                    } else {
                                        Color.White
                                    }
                                )
                            )

                            Text(
                                text = "Heure de départ : 18:44:00",
                                modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
                                style = MaterialTheme.typography.titleSmall,
                                textAlign = TextAlign.Center,
                                color = if (!isDark) {
                                    colorResource(R.color.black40)
                                } else {
                                    Color.White
                                }
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
                                    color = if (!isDark) {
                                        colorResource(R.color.black40)
                                    } else {
                                        Color.White
                                    }
                                )
                            )

                            Text(
                                text = "Date d'arrivée : ",
                                modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
                                style = MaterialTheme.typography.titleSmall,
                                textAlign = TextAlign.Center,
                                color = if (!isDark) {
                                    colorResource(R.color.black40)
                                } else {
                                    Color.White
                                }
                            )
                        }

                        Row(
                            modifier = Modifier
                                .padding(9.dp)
                                .animateContentSize(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                imageVector = Icons.Outlined.Timer,
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.tint(
                                    color = if (!isDark) {
                                        colorResource(R.color.black40)
                                    } else {
                                        Color.White
                                    }
                                )
                            )

                            Text(
                                text = "Heure d'arrivée : 18:44:00",
                                modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
                                style = MaterialTheme.typography.titleSmall,
                                textAlign = TextAlign.Center,
                                color = if (!isDark) {
                                    colorResource(R.color.black40)
                                } else {
                                    Color.White
                                }
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
                                    color = if (!isDark) {
                                        colorResource(R.color.black40)
                                    } else {
                                        Color.White
                                    }
                                )
                            )

                            Text(
                                text = "${screenState.announcement[0].price} €/Kg",
                                modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
                                style = MaterialTheme.typography.titleSmall,
                                textAlign = TextAlign.Center,
                                color = if (!isDark) {
                                    colorResource(R.color.black40)
                                } else {
                                    Color.White
                                }
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
                                    color = if (!isDark) {
                                        colorResource(R.color.black40)
                                    } else {
                                        Color.White
                                    }
                                )
                            )

                            Text(
                                text = "${screenState.announcement[0].price} Kg (max)",
                                modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
                                style = MaterialTheme.typography.titleSmall,
                                textAlign = TextAlign.Center,
                                color = if (!isDark) {
                                    colorResource(R.color.black40)
                                } else {
                                    Color.White
                                }
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
                                    color = if (!isDark) {
                                        colorResource(R.color.black40)
                                    } else {
                                        Color.White
                                    }
                                )
                            )

                            Text(
                                text = "Rdv de départ : lieux 1",
                                modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
                                style = MaterialTheme.typography.titleSmall,
                                textAlign = TextAlign.Center,
                                color = if (!isDark) {
                                    colorResource(R.color.black40)
                                } else {
                                    Color.White
                                }
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
                                    color = if (!isDark) {
                                        colorResource(R.color.black40)
                                    } else {
                                        Color.White
                                    }
                                )
                            )

                            Text(
                                text = "Rdv d'arrivée : lieux 2",
                                modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
                                style = MaterialTheme.typography.titleSmall,
                                textAlign = TextAlign.Center,
                                color = if (!isDark) {
                                    colorResource(R.color.black40)
                                } else {
                                    Color.White
                                }
                            )
                        }
                    }



                    LaunchedEffect(true) {
                        delay(3)
                        visibleImage = true
                    }
                }
            )

        },
        content = { innerPadding ->

        })
}