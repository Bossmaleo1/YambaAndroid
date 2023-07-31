package com.android.yambasama.ui.views.bottomnavigationviews.searchview

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.yambasama.R
import com.android.yambasama.presentation.viewModel.address.AddressViewModel
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModel
import com.android.yambasama.presentation.viewModel.user.UserViewModel
import com.android.yambasama.ui.UIEvent.Event.AddressEvent
import com.android.yambasama.ui.UIEvent.Event.AuthEvent
import com.android.yambasama.ui.UIEvent.UIEvent
import com.android.yambasama.ui.util.Util
import com.android.yambasama.ui.views.shimmer.AddressShimmer
import com.android.yambasama.ui.views.viewsError.networkError
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale


@ExperimentalMaterial3Api
@Composable
fun SearchAddress(
    navController: NavHostController,
    addressViewModel: AddressViewModel,
    userViewModel: UserViewModel,
    searchFormViewModel: SearchFormViewModel
) {
    var visibleSearch by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    var searchAddress by rememberSaveable { mutableStateOf("") }
    val screenState = addressViewModel.screenState.value
    val screenStateUser = userViewModel.screenState.value
    val scaffoldState = rememberScaffoldState()
    val util = Util()
    val isDark = isSystemInDarkTheme()

    userViewModel.onEvent(AuthEvent.GetSavedToken)
    if (screenStateUser.tokenRoom.isNotEmpty()) {
        userViewModel.onEvent(AuthEvent.GetSavedUserByToken)
    }

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

        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = MaterialTheme.colorScheme.background,
            topBar = {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {

                    Row(modifier = Modifier.padding(bottom = 10.dp)) {

                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = "",
                                tint = if (!isDark) {
                                    colorResource(R.color.black40)
                                } else {
                                    Color.White
                                }
                            )
                        }

                        OutlinedTextField(
                            value = searchAddress,
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 12.sp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            ),
                            onValueChange = {
                                searchAddress = it
                                if (screenStateUser.tokenRoom.isNotEmpty()) {
                                    addressViewModel.onEvent(
                                        AddressEvent.SearchValueEntered(
                                            value = it,
                                            token = screenStateUser.tokenRoom[0].token,
                                            locale = "${Locale.getDefault().language}"
                                        )
                                    )
                                }

                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            placeholder = {
                                Text(
                                    text = if (searchFormViewModel.screenState.value.departureOrDestination == 1) {
                                        stringResource(id = R.string.search_departure)
                                    } else {
                                        stringResource(id = R.string.search_destination)
                                    }, fontSize = 12.sp
                                )
                            },
                            leadingIcon = {
                                IconButton(onClick = { }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Search,
                                        contentDescription = "",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 0.dp, bottom = 0.dp, start = 0.dp, end = 30.dp)
                                .height(60.dp),
                            shape = RoundedCornerShape(22.dp)
                        )
                    }

                }

            },
            content = { innerPadding ->
                LaunchedEffect(key1 = true) {
                    if (screenStateUser.tokenRoom.isNotEmpty()) {
                        addressViewModel.onEvent(
                            AddressEvent.AddressInit(
                                value = screenState.searchInputValue,
                                token = screenStateUser.tokenRoom[0].token,
                                locale = screenState.locale
                            )
                        )
                    }
                }


                LazyColumn(
                    contentPadding = PaddingValues(
                        top = 0.dp,
                        bottom = innerPadding.calculateBottomPadding() + 100.dp
                    ),
                    state = listState
                ) {
                    items(screenState.addressList) { address ->
                        SearchTownItem(
                            navController = navController,
                            address = address,
                            searchFormViewModel = searchFormViewModel,
                            util = util
                        )
                    }

                    if (screenState.isLoad) {
                        items(count = 1) {
                            AddressShimmer()
                        }
                    }

                    if (!screenState.isNetworkConnected) {
                        items(count = 1) {
                            networkError(
                                title = stringResource(R.string.network_error),
                                iconValue = 0
                            )
                        }
                    } else if (screenState.isNetworkError) {
                        items(count = 1) {
                            networkError(
                                title = stringResource(R.string.is_connect_error),
                                iconValue = 1
                            )
                        }
                    } else if (screenState.isInternalError) {
                        items(count = 1) {
                            networkError(
                                title = "Internal Error, Error 500",
                                iconValue = 1
                            )
                        }
                    }

                }

                if (screenState.isNetworkError) {
                    addressViewModel.onEvent(AddressEvent.IsNetworkError)
                } else if (!screenState.isNetworkConnected) {
                    addressViewModel.onEvent(AddressEvent.IsNetworkConnected)
                } else if (screenState.isInternalError) {
                    addressViewModel.onEvent(AddressEvent.IsInternalError)
                }

                LaunchedEffect(key1 = true) {

                    addressViewModel.uiEventFlow.collectLatest { event ->
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
            })

        LaunchedEffect(key1 = true) {
            searchFormViewModel.uiEventFlow.collectLatest { event ->
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
    }

    LaunchedEffect(true) {
        delay(3)
        visibleSearch = true
    }
}