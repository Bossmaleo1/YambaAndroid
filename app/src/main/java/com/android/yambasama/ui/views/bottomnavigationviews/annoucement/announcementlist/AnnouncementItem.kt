package com.android.yambasama.ui.views.bottomnavigationviews.annoucement.announcementDetails.announcementlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberAsyncImagePainter
import com.android.yambasama.BuildConfig
import com.android.yambasama.R
import com.android.yambasama.data.model.dataRemote.Announcement
import com.android.yambasama.presentation.viewModel.announcement.AnnouncementViewModel
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModel
import com.android.yambasama.ui.UIEvent.Event.AnnouncementEvent
import com.android.yambasama.ui.util.Util
import com.android.yambasama.ui.views.model.Route
import kotlinx.coroutines.delay
import java.util.*


@Composable
fun getOurUserImage(announcement: Announcement): Painter {
    val isDark = isSystemInDarkTheme()
    if (announcement.user.images.isEmpty()) {
         //if (isDark) {
        return   painterResource(id = R.drawable.ic_profile_colorier)
       /* } else {
            painterResource(id = R.drawable.ic_profile)
        }*/
    }
    return rememberAsyncImagePainter("${BuildConfig.BASE_URL_DEV}/images/${announcement.user.images[announcement.user.images.size - 1].imageName}")
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun AnnouncementItem(
    navController: NavHostController,
    announcement: Announcement,
    searchFormViewModel: SearchFormViewModel,
    annoucementViewModel: AnnouncementViewModel,
    util: Util
) {

    var visibleImage by remember { mutableStateOf(false) }
    val userName by rememberSaveable { mutableStateOf("${announcement.user.firstName} ${announcement.user.lastName}") }
    val isDark = isSystemInDarkTheme()
    val destinationDate by rememberSaveable { mutableStateOf(util.getDateTimeFormatter(announcement.arrivingTime)) }
    val postDateTime by rememberSaveable { mutableStateOf(util.getDateFormatter(announcement.published)) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(2.5.dp)
            .clickable {
                annoucementViewModel.onEvent(
                    AnnouncementEvent.ItemClicked(announcement = announcement)
                )
                navController.navigate(Route.detailsView)
            },
        shape = RoundedCornerShape(corner = CornerSize(10.dp))
    ) {

        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
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
                    painter = getOurUserImage(announcement),
                    contentDescription = "Profile picture description",
                    modifier = Modifier
                        .padding(4.dp)
                        .height(50.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(25.dp)))
                        .clickable {
                            navController.navigate(Route.accountDetailView)
                        },
                    contentScale = ContentScale.Crop,
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
                        modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

        }

        Row {
            Divider(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(bottom = 10.dp, top = 0.dp)
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
                color = MaterialTheme.colorScheme.primary,
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
                text = destinationDate,
                modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
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
                text = "${announcement.price} €/Kg",
                modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

    LaunchedEffect(true) {
        delay(3)
        visibleImage = true
    }
}