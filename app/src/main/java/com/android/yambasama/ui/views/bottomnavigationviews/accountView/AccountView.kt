package com.android.yambasama.ui.views.bottomnavigationviews.accountView

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.android.yambasama.BuildConfig
import com.android.yambasama.R
import com.android.yambasama.data.model.dataLocal.UserRoom
import com.android.yambasama.ui.views.model.Route


@Composable
fun getOurUserImage(user: UserRoom): Painter {
    if (user.imageUrl.isNullOrBlank()) {
        return painterResource(id = R.drawable.ic_profile_colorier)
    }
    return rememberAsyncImagePainter("${BuildConfig.BASE_URL_DEV}/images/${user.imageUrl}")
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun AccountView(
    navController: NavHostController,
    user: UserRoom
) {
    val isDark = isSystemInDarkTheme()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
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
                    IconButton(
                        modifier = Modifier,
                        onClick = {
                            navController.navigate(Route.accountEditView)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Localized description",
                            tint = if (!isDark) {
                                colorResource(R.color.black40)
                            } else {
                                Color.White
                            }
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.profile),
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
        content = {
            // Creating a Column Layout
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                val lazyListState = rememberLazyListState()
                var scrolledY = 0f
                var prevousOffset = 0
                LazyColumn(
                    Modifier.fillMaxSize(),
                    lazyListState) {
                    item {
                        Image(
                            painter = getOurUserImage(user),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .graphicsLayer {
                                    scrolledY += lazyListState.firstVisibleItemScrollOffset - prevousOffset
                                    translationY = scrolledY * 0.5f
                                    prevousOffset = lazyListState.firstVisibleItemScrollOffset
                                }
                                .height(400.dp)
                                .fillMaxWidth()
                        )
                    }

                    item {

                        Row(horizontalArrangement = Arrangement.Center) {
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(4.dp),
                                shape = RoundedCornerShape(corner = CornerSize(10.dp))) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    verticalArrangement = Arrangement.Center){
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .wrapContentHeight()
                                            .padding(18.dp)) {
                                        Icon(
                                            imageVector = Icons.Filled.Verified,
                                            contentDescription = null,
                                            tint = Color.Green,
                                        )

                                        Text(
                                            text = "Pièce d'identitée verifiée",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color.Green,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                }
                            }
                        }

                    }

                    item {

                        Row(horizontalArrangement = Arrangement.Center) {
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(4.dp),
                                shape = RoundedCornerShape(corner = CornerSize(10.dp))) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    verticalArrangement = Arrangement.Center){
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .wrapContentHeight()
                                            .padding(18.dp)) {
                                        Icon(
                                            imageVector = Icons.Filled.Block,
                                            contentDescription = null,
                                            tint = Color.Red,
                                        )

                                        Text(
                                            text = "Faites vérifier votre pièce d'identitée",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color.Red,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                }
                            }
                        }

                    }

                    item {

                        Row(horizontalArrangement = Arrangement.Center) {
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(4.dp),
                                shape = RoundedCornerShape(corner = CornerSize(10.dp))) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    verticalArrangement = Arrangement.Center){
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .wrapContentHeight()
                                            .padding(18.dp)) {
                                        Icon(
                                            imageVector = Icons.Filled.LocalLibrary,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                        )

                                        Text(
                                            text = "Mention: Débutant",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                }
                            }
                        }

                    }

                    item {

                        Row(horizontalArrangement = Arrangement.Center) {
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(4.dp),
                                shape = RoundedCornerShape(corner = CornerSize(10.dp))) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    verticalArrangement = Arrangement.Center){
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .wrapContentHeight()
                                            .padding(18.dp)) {
                                    Column {
                                        Text(
                                            text = "Note: 4.2/5, 5 Avis",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Normal
                                        )
                                        Row {
                                            Icon(
                                                imageVector = Icons.Filled.Star,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary,
                                            )

                                            Icon(
                                                imageVector = Icons.Filled.Star,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary,
                                            )

                                            Icon(
                                                imageVector = Icons.Filled.Star,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary,
                                            )

                                            Icon(
                                                imageVector = Icons.Filled.Star,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary,
                                            )

                                            Icon(
                                                imageVector = Icons.Filled.StarHalf,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary,
                                            )
                                        }
                                    }
                                    }
                                }
                            }
                        }

                    }

                    item {

                        Row(horizontalArrangement = Arrangement.Center) {
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(4.dp),
                                shape = RoundedCornerShape(corner = CornerSize(10.dp))) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    verticalArrangement = Arrangement.Center){
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .wrapContentHeight()
                                            .padding(18.dp)) {
                                        Icon(
                                            imageVector = Icons.Filled.Translate,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                        )

                                        Text(
                                            text = "En",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                }
                            }
                        }

                    }

                    item {

                        Row(horizontalArrangement = Arrangement.Center) {
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(4.dp),
                                shape = RoundedCornerShape(corner = CornerSize(10.dp))) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    verticalArrangement = Arrangement.Center){
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .wrapContentHeight()
                                            .padding(18.dp),
                                        horizontalArrangement = Arrangement.Center) {
                                        Icon(
                                            imageVector = Icons.Filled.DarkMode,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                        )

                                        Text(
                                            text = "Mode Nuit",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Normal
                                        )
                                        
                                        Spacer(modifier = Modifier.weight(1f))

                                        Row(horizontalArrangement = Arrangement.Center) {
                                            Switch(
                                                checked = isDark,
                                                onCheckedChange = {

                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    )
}
