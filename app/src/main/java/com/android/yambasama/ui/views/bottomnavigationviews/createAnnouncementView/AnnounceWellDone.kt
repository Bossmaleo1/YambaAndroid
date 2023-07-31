package com.android.yambasama.ui.views.bottomnavigationviews.createAnnouncementView

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.yambasama.R
import com.android.yambasama.presentation.viewModel.announcement.AnnouncementViewModel
import com.android.yambasama.ui.views.model.Route
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@ExperimentalMaterial3Api
@Composable
fun AnnouncementWellDone(
    navController: NavHostController,
    announcementViewModel: AnnouncementViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val isDark = isSystemInDarkTheme()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var visibleAnimate by remember { mutableStateOf(false) }

    class SnackbarVisual(
        override val message: String
    ) : SnackbarVisuals {
        override val actionLabel: String
            get() = "OK"
        override val withDismissAction: Boolean
            get() = false
        override val duration: SnackbarDuration
            get() = SnackbarDuration.Long
    }

    AnimatedVisibility(
        visible = visibleAnimate,
        enter = slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
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
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            //navController.navigateUp()
                            navController.navigate(Route.homeView)
                        }) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = "",
                                tint = if (!isDark) {
                                    colorResource(R.color.black40)
                                } else {
                                    Color.White
                                }
                            )
                        }
                    },
                    actions = {

                    },
                    scrollBehavior = scrollBehavior,
                    title = {

                    }
                )
            },
            snackbarHost = {
                // reuse default SnackbarHost to have default animation and timing handling
                SnackbarHost(snackbarHostState) { data ->
                    Snackbar(
                        modifier = Modifier
                            .padding(12.dp),
                        action = {
                            Text(data.visuals.actionLabel ?: "")
                        }
                    ) {
                        Text(data.visuals.message)
                    }
                }
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.TaskAlt,
                            contentDescription = "Testing !! Testing !!",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                        )
                    }
                }

            }
        )
    }

    LaunchedEffect(key1 = true) {
        scope.launch {
            snackbarHostState.showSnackbar(
                SnackbarVisual(
                    "Votre annonce vient d'être publier avec succès !!"
                )
            )
        }
    }

    LaunchedEffect(
        key1 = true
    ) {
        delay(3)
        visibleAnimate = true
    }

}