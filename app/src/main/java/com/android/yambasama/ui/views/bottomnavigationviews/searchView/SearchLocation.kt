package com.android.yambasama.ui.views.bottomnavigationviews.searchView

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.yambasama.R
import kotlinx.coroutines.delay
import java.util.*


@ExperimentalMaterial3Api
@Composable
fun SearchLocation(navController: NavHostController) {
    var visibleSearch by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var searchLocation by rememberSaveable { mutableStateOf("") }


    AnimatedVisibility(
        visible = visibleSearch,
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

        Scaffold(topBar = {
            Column (
                modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
                Row {
                    OutlinedTextField(
                        value = searchLocation,
                        singleLine = true,
                        textStyle = TextStyle( fontSize = 12.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        ),
                        onValueChange = {locationName ->

                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        placeholder = { Text(text= stringResource(id = R.string.search), fontSize = 12.sp) },
                        leadingIcon = {
                            IconButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 10.dp, start = 30.dp, end = 30.dp)
                            .height(45.dp),
                        shape = RoundedCornerShape(22.dp)
                    )
                }
            }

        },
            content= {innerPadding ->
                LazyColumn(contentPadding = innerPadding, state = listState) {
                    items(1000) {location ->
                        SearchTownItem()
                    }
                }
            })
    }



    LaunchedEffect(true) {
        delay(3)
        visibleSearch = true
    }
}