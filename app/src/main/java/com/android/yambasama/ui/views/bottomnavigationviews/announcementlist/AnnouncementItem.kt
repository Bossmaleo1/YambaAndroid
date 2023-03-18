package com.android.yambasama.ui.views.bottomnavigationviews.announcementlist

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
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberAsyncImagePainter
import com.android.yambasama.BuildConfig
import com.android.yambasama.R
import com.android.yambasama.data.model.dataRemote.Address
import com.android.yambasama.data.model.dataRemote.Announcement
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun getOurUserImage(announcement: Announcement): Painter {
    if (announcement.user.images.isEmpty()) {
        return painterResource(id = R.drawable.ic_profile)
    }
    return rememberAsyncImagePainter("${BuildConfig.BASE_URL_DEV}/images/${announcement.user.images[announcement.user.images.size - 1].imageName}")
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun AnnouncementItem(
    navController: NavHostController,
    announcement: Announcement,
    searchFormViewModel: SearchFormViewModel
) {

    val formatter: SimpleDateFormat = SimpleDateFormat("EEE d MMM yy", Locale.getDefault())
    val published = formatter.format(announcement.published)
    var visibleImage by remember { mutableStateOf(false) }
    val userName by rememberSaveable { mutableStateOf("${announcement.user.firstName} ${announcement.user.lastName}") }
    val isDark = isSystemInDarkTheme()
    val postTime by rememberSaveable { mutableStateOf(published) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(2.5.dp),
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
                        text = postTime,
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
                color = if(!isDark) { Color.White } else { Color.Black},
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
                    color = if (!isDark) {
                        colorResource(R.color.black40)
                    } else {
                        Color.White
                    }
                )
            )

            Text(
                text = "Sidney MALEO",
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
                text = "Sidney MALEO",
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
                imageVector = Icons.Outlined.AccessTime,
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
                text = postTime,
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
                text = postTime,
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
        /*Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = "Hello world 1")
            Text(text = "Hello world 2")
        }*/

    }

    LaunchedEffect(true) {
        delay(3)
        visibleImage = true
    }
}