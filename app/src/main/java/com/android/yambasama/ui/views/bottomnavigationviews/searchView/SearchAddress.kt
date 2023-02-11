package com.android.yambasama.ui.views.bottomnavigationviews.searchView

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.yambasama.R
import com.android.yambasama.presentation.viewModel.address.AddressViewModel
import com.android.yambasama.presentation.viewModel.user.UserViewModel
import com.android.yambasama.ui.UIEvent.Event.AddressEvent
import com.android.yambasama.ui.UIEvent.UIEvent
import com.android.yambasama.ui.views.shimmer.AddressShimmer
import com.android.yambasama.ui.views.viewsError.networkError
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest


@ExperimentalMaterial3Api
@Composable
fun SearchAddress(
    navController: NavHostController,
    addressViewModel: AddressViewModel,
    userViewModel: UserViewModel
) {
    var visibleSearch by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    var searchAddress by rememberSaveable { mutableStateOf("") }
    val token by userViewModel.tokenValue.observeAsState()
    val screenState = addressViewModel.screenState.value
    val scaffoldState = rememberScaffoldState()

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

        Scaffold(scaffoldState = scaffoldState,
            topBar = {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {

                    Row(modifier = Modifier.padding(bottom = 10.dp)) {

                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        OutlinedTextField(
                            value = searchAddress,
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 12.sp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            ),
                            onValueChange = {
                                searchAddress = it
                                addressViewModel.onEvent(
                                    AddressEvent.SearchValueEntered(
                                        value = it,
                                        token =token?.token!!
                                    )
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.search),
                                    fontSize = 12.sp
                                )
                            },
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
                                .padding(top = 0.dp, bottom = 0.dp, start = 0.dp, end = 30.dp)
                                .height(60.dp),
                            shape = RoundedCornerShape(22.dp)
                        )
                    }

                }

            },
            content = { innerPadding ->

                LaunchedEffect(screenState.initCall == 1) {
                    addressViewModel.onEvent(
                        AddressEvent.AddressInit(
                            value = screenState.searchInputValue,
                            token =token?.token!!
                        )
                    )
                }


                LazyColumn(
                    contentPadding = PaddingValues(
                        top = 0.dp,
                        bottom = innerPadding.calculateBottomPadding() + 100.dp
                    ),
                    state = listState
                ) {
                    items(screenState.addressList) { address ->
                        SearchTownItem(address)
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
                    }

                }

                if (screenState.isNetworkError) {
                    addressViewModel.onEvent(AddressEvent.IsNetworkError)
                } else if (!screenState.isNetworkConnected) {
                    addressViewModel.onEvent(AddressEvent.IsNetworkConnected)
                }

                LaunchedEffect(key1 = true) {
                    addressViewModel.uiEventFlow.collectLatest {event->
                        when(event) {
                            is UIEvent.ShowMessage-> {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = event.message
                                )
                            }
                        }
                    }
                }
            })
    }

    LaunchedEffect(true) {
        delay(3)
        visibleSearch = true
    }
}