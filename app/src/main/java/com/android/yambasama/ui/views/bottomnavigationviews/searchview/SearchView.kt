package com.android.yambasama.ui.views.bottomnavigationviews

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.android.yambasama.R
import com.android.yambasama.presentation.util.LocationData
import com.android.yambasama.presentation.util.LocationLiveData
import com.android.yambasama.presentation.viewModel.announcement.AnnouncementViewModel
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModel
import com.android.yambasama.ui.UIEvent.Event.SearchFormEvent
import com.android.yambasama.ui.util.Util
import com.android.yambasama.ui.views.model.Route
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun SearchView(
    navController: NavHostController,
    searchFormViewModel: SearchFormViewModel,
    announcementViewModel: AnnouncementViewModel,
    util: Util
) {
    var visibleForm by remember { mutableStateOf(false) }
    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }
    val formatter: SimpleDateFormat = SimpleDateFormat("EEE d MMM yy", Locale.getDefault())
    val openDialog = remember { mutableStateOf(false) }

    //We request the permission
    RequestLocationPermission()

    if (searchFormViewModel.screenState.value.dateDialog !== null) {
        mDate.value = formatter.format(searchFormViewModel.screenState.value.dateDialog!!)
    }

    AnimatedVisibility(
        visible = visibleForm,
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
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            OutlinedButton(
                modifier = Modifier
                    .width(280.dp)
                    .height(55.dp),
                border = if (searchFormViewModel.screenState.value.isDepartureError) {
                    BorderStroke(1.dp, color = Color.Red)
                } else {
                    BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary)
                },
                shape = RoundedCornerShape(30),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.2f
                    )
                ),
                onClick = {
                    searchFormViewModel.screenState.value.departureOrDestination = 1
                    navController.navigate(Route.searchLocalizeView)
                }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.FlightTakeoff,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(
                            text = if (searchFormViewModel.screenState.value.addressDeparture !== null) {
                                "${searchFormViewModel.screenState.value.addressDeparture?.townName} ( ${util.getCountry(searchFormViewModel.screenState.value.addressDeparture!!.code)} ( ${searchFormViewModel.screenState.value.addressDeparture?.airportName}, ${searchFormViewModel.screenState.value.addressDeparture?.airportCode} ))"
                            } else {
                                stringResource(R.string.departure)
                            },
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            if (searchFormViewModel.screenState.value.isDepartureError) {
                Text(
                    color = Color.Red,
                    text = stringResource(R.string.departureDateError)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                modifier = Modifier
                    .width(280.dp)
                    .height(55.dp),
                border =
                if (searchFormViewModel.screenState.value.isDestinationError) {
                    BorderStroke(1.dp, color = Color.Red)
                } else {
                    BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary)
                },
                shape = RoundedCornerShape(30),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.2f
                    )
                ),
                onClick = {
                    searchFormViewModel.screenState.value.departureOrDestination = 2
                    navController.navigate(Route.searchLocalizeView)
                }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.FlightLand,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(
                            text = if (searchFormViewModel.screenState.value.addressDestination !== null) {
                                "${searchFormViewModel.screenState.value.addressDestination?.townName} ( ${util.getCountry(searchFormViewModel.screenState.value.addressDestination!!.code)} ( ${searchFormViewModel.screenState.value.addressDestination?.airportName}, ${searchFormViewModel.screenState.value.addressDestination?.airportCode} ))"
                            } else {
                                stringResource(R.string.destination)
                            },
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            if (searchFormViewModel.screenState.value.isDestinationError) {
                Text(
                    color = Color.Red,
                    text = stringResource(R.string.destinationDateError)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                modifier = Modifier
                    .width(280.dp)
                    .height(55.dp),
                border = if (searchFormViewModel.screenState.value.isDepartureTimeError) {
                    BorderStroke(1.dp, color = Color.Red)
                } else {
                    BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary)
                },
                shape = RoundedCornerShape(30),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.2f
                    )
                ),
                onClick = {
                    openDialog.value = true
                }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.Today,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(
                            text = if (mDate.value.length > 3) {
                                mDate.value
                            } else {
                                stringResource(R.string.travel_date)
                            },
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            if (searchFormViewModel.screenState.value.isDepartureTimeError) {
                Text(
                    color = Color.Red,
                    text = stringResource(R.string.datetravelError)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                modifier = Modifier
                    .width(280.dp),
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                onClick = {
                    searchFormViewModel.onEvent(
                        SearchFormEvent.IsTravelDateUpdated(
                            isTravelDate = searchFormViewModel.screenState.value.dateDialog === null,
                            isDeparture = searchFormViewModel.screenState.value.addressDeparture === null,
                            isDestination = searchFormViewModel.screenState.value.addressDestination === null
                        )
                    )

                    //we initialize some params
                    announcementViewModel.screenState.value.currentPage = 1
                    announcementViewModel.screenState.value.announcementList = mutableStateListOf()
                    announcementViewModel.screenState.value.announcement = mutableListOf()

                    if (
                        searchFormViewModel.screenState.value.dateDialog !== null
                        && searchFormViewModel.screenState.value.addressDestination !== null
                        && searchFormViewModel.screenState.value.addressDeparture !== null
                    ) {
                        navController.navigate(Route.announcementList)
                    }
                }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = stringResource(R.string.re_search))
            }
        }

        if (openDialog.value) {
            val datePickerState = rememberDatePickerState()
            val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
            DatePickerDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onDismissRequest.
                    openDialog.value = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                            val date = datePickerState.selectedDateMillis?.let { Date(it) }
                            mDate.value = date?.let { util.getDateFormatter(it) }.toString()
                            searchFormViewModel.screenState.value.dateDialog = date
                            val cal: Calendar =  Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                            cal.time = date
                            val monthCount = (cal.get(Calendar.MONTH)+1)
                            val monthDay: String = if (monthCount >= 10) {
                                "${(cal.get(Calendar.MONTH)+1)}"
                            } else {
                                "0${(cal.get(Calendar.MONTH)+1)}"
                            }
                            val departureTimeAfter = "${cal.get(Calendar.YEAR)}-${monthDay}-${cal.get(Calendar.DAY_OF_MONTH)}T00:00:00"
                            val departureTimeBefore = "${cal.get(Calendar.YEAR)}-${monthDay}-${cal.get(Calendar.DAY_OF_MONTH)}T23:59:00"
                            searchFormViewModel.screenState.value.arrivingTimeAfter = departureTimeAfter
                            searchFormViewModel.screenState.value.arrivingTimeBefore = departureTimeBefore
                        },
                        enabled = confirmEnabled.value
                    ) {
                        Text(text=stringResource(R.string.validate))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text(text=stringResource(R.string.cancel))
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

    }
    LaunchedEffect(true) {
        delay(3)
        visibleForm = true
    }

}


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission() {
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    if (locationPermissionsState.allPermissionsGranted) {
        Log.d("MALEO939393", "The permission is allow")
        LocationLiveData(LocalContext.current).observe(LocalContext.current as LifecycleOwner) {
            handleLocationData(it!!)
        }
    } else {
        CoroutineScope(Dispatchers.Main).launch {
            locationPermissionsState.launchMultiplePermissionRequest()
        }


    }

    if (locationPermissionsState.shouldShowRationale) {
        Log.d("MALEO9393", " important. Please grant all of them for the app to function properly.")
    } else {
        Log.d("MALEO9393", "denied. The app cannot function without them.")
    }
}

fun handleLocationData(locationData: LocationData) {
    if(handleLocationException(locationData.exception)) {
        return
    }

    Log.d("MALEO9393", "Last location ${locationData.addressLocation}")
    //Log.d("MALEO93939393939393", "Latitude : ${locationData.location!!.latitude}")
    //Log.d("MALEO93939393939393", "Longitude :  ${locationData.location!!.longitude}")
    Log.d("MALEO9393", "Last location ${locationData.addressLocation!!.country}")
    Log.d("MALEO9393", "Last location ${locationData.addressLocation!!.city}")
}


fun handleLocationException(exception: Exception?): Boolean {
    exception ?: return false
    when(exception) {
        is SecurityException -> {
            Log.d("MALEO9393", "Testing !!")
        }
    }
    return true
}