package com.android.yambasama.ui.views.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.yambasama.R
import com.android.yambasama.data.model.dataLocal.UserRoom

@Composable
@ExperimentalMaterial3Api
fun HomeUserProfil(
    userRoom: UserRoom,
    navController: NavHostController,
    visibleSearch: Boolean
) {

    if (userRoom !== null) {
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
                HomeUserImage(
                    navController = navController,
                    userRoom = userRoom
                )
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
                    text = "${userRoom!!.firstName} ${userRoom!!.lastName}",
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}