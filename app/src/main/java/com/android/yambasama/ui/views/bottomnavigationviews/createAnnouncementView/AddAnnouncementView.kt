package com.android.yambasama.ui.views.bottomnavigationviews

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.EuroSymbol
import androidx.compose.material.icons.outlined.FlightLand
import androidx.compose.material.icons.outlined.FlightTakeoff
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.NextPlan
import androidx.compose.material.icons.outlined.SaveAs
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.android.yambasama.R
import com.android.yambasama.data.model.api.NumberOfKgBody
import com.android.yambasama.presentation.viewModel.announcement.AnnouncementViewModel
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModel
import com.android.yambasama.presentation.viewModel.user.UserViewModel
import com.android.yambasama.ui.UIEvent.Event.AnnouncementEvent
import com.android.yambasama.ui.UIEvent.Event.SearchFormEvent
import com.android.yambasama.ui.util.Util
import com.android.yambasama.ui.views.model.Route
import com.android.yambasama.ui.views.utils.TimePickerDialog
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterial3Api
@Composable
fun AddAnnouncementView(
    util: Util,
    navController: NavHostController,
    visibleCurrentForm: MutableState<Boolean>,
    visiblePreviousForm: MutableState<Boolean>,
    searchFormViewModel: SearchFormViewModel,
    announcementViewModel: AnnouncementViewModel,
    userViewModel: UserViewModel
) {

    val departureDateOpenDialog = remember { mutableStateOf(false) }
    val destinationDateOpenDialog = remember { mutableStateOf(false) }
    val departureTimeOpenDialog = remember { mutableStateOf(false) }
    val destinationTimeOpenDialog = remember { mutableStateOf(false) }
    val departureTimePickerState = rememberTimePickerState(
        initialHour = 12
    )
    val destinationTimePickerState = rememberTimePickerState(
        initialHour = 12
    )
    val configuration = LocalConfiguration.current


    val mDepartureDate = remember { mutableStateOf("") }
    val mDepartureTime = remember { mutableStateOf("") }
    val mDestinationDate = remember { mutableStateOf("") }
    val mDestinationTime = remember { mutableStateOf("") }


    val mArrivingDate = remember { mutableStateOf("") }
    val formatter: SimpleDateFormat = SimpleDateFormat("EEE d MMM yy", Locale.getDefault())
    var showDepartureTimePicker by remember { mutableStateOf(false) }
    val timeformatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    val timeZoneId = TimeZone.getDefault().id

    var priceField by remember { mutableStateOf("0") }
    var numberOfKgField by remember { mutableStateOf("0") }
    var meetingPlace1 by remember { mutableStateOf("") }
    var meetingPlace2 by remember { mutableStateOf("") }
    var visibleForm by remember { mutableStateOf(false) }

    val screenState = userViewModel.screenState.value


    if (searchFormViewModel.screenState.value.dateDialogDepartureCreated !== null) {
        mDepartureDate.value =
            formatter.format(searchFormViewModel.screenState.value.dateDialogDepartureCreated!!)
    }

    if (searchFormViewModel.screenState.value.dateDialogDestinationCreated !== null) {
        mDestinationDate.value =
            formatter.format(searchFormViewModel.screenState.value.dateDialogDestinationCreated!!)
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

        AnimatedVisibility(
            visible = visibleCurrentForm.value,
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
                    border = if (searchFormViewModel.screenState.value.isDepartureCreatedError) {
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
                        searchFormViewModel.screenState.value.departureOrDestination = 3
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
                                text = if (searchFormViewModel.screenState.value.addressDepartureCreated !== null) {
                                    "${searchFormViewModel.screenState.value.addressDepartureCreated?.townName} ( ${
                                        util.getCountry(
                                            searchFormViewModel.screenState.value.addressDepartureCreated!!.code
                                        )
                                    } ( ${searchFormViewModel.screenState.value.addressDepartureCreated?.airportName}, ${searchFormViewModel.screenState.value.addressDepartureCreated?.airportCode} ))"
                                } else {
                                    stringResource(R.string.departure)
                                },
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                }

                if (searchFormViewModel.screenState.value.isDepartureCreatedError) {
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
                    border = if (searchFormViewModel.screenState.value.isDestinationCreatedError) {
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
                        searchFormViewModel.screenState.value.departureOrDestination = 4
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
                                text = if (searchFormViewModel.screenState.value.addressDestinationCreated !== null) {
                                    "${searchFormViewModel.screenState.value.addressDestinationCreated?.townName} ( ${
                                        util.getCountry(
                                            searchFormViewModel.screenState.value.addressDestinationCreated!!.code
                                        )
                                    } ( ${searchFormViewModel.screenState.value.addressDestinationCreated?.airportName}, ${searchFormViewModel.screenState.value.addressDestinationCreated?.airportCode} ))"
                                } else {
                                    stringResource(R.string.destination)
                                },
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                }

                if (searchFormViewModel.screenState.value.isDestinationCreatedError) {
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
                    border = if (searchFormViewModel.screenState.value.isDepartureDateCreatedError) {
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
                        departureDateOpenDialog.value = true
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
                                text = if (mDepartureDate.value.length > 3) {
                                    mDepartureDate.value
                                } else {
                                    stringResource(id = R.string.departure_date)
                                },
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                }

                if (searchFormViewModel.screenState.value.isDepartureDateCreatedError) {
                    Text(
                        color = Color.Red,
                        text = stringResource(id = R.string.departureDateError)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    modifier = Modifier
                        .width(280.dp)
                        .height(55.dp),
                    border = if (searchFormViewModel.screenState.value.isDepartureTimeCreatedError) {
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
                        departureTimeOpenDialog.value = true
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
                                imageVector = Icons.Outlined.Timer,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text(
                                text = if (mDepartureTime.value.length > 3) {
                                    mDepartureTime.value
                                } else {
                                    stringResource(id = R.string.departure_time)
                                },
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                }

                if (searchFormViewModel.screenState.value.isDepartureTimeCreatedError) {
                    Text(
                        color = Color.Red,
                        text = stringResource(id = R.string.departureTimeError)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    modifier = Modifier
                        .width(280.dp)
                        .height(55.dp),
                    border = if (searchFormViewModel.screenState.value.isDestinationDateCreatedError) {
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
                        destinationDateOpenDialog.value = true
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
                                text = if (mDestinationDate.value.length > 3) {
                                    mDestinationDate.value
                                } else {
                                    stringResource(id = R.string.arrival_date)
                                },
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                }

                if (searchFormViewModel.screenState.value.isDestinationDateCreatedError) {
                    Text(
                        color = Color.Red,
                        text = stringResource(id = R.string.destinationDateError)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    modifier = Modifier
                        .width(280.dp)
                        .height(55.dp),
                    border = if (searchFormViewModel.screenState.value.isDestinationTimeCreatedError) {
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
                        destinationTimeOpenDialog.value = true
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
                                imageVector = Icons.Outlined.Timer,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text(
                                text = if (mDestinationTime.value.length > 3) {
                                    mDestinationTime.value
                                } else {
                                    stringResource(id = R.string.departure_time)
                                },
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                }

                if (searchFormViewModel.screenState.value.isDestinationTimeCreatedError) {
                    Text(
                        color = Color.Red,
                        text = stringResource(id = R.string.departureTimeDateError)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    modifier = Modifier
                        .width(280.dp),
                    border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                    onClick = {

                        searchFormViewModel.onEvent(
                            SearchFormEvent.IsValidFirstFormStepp(
                                isDepartureCreatedError = searchFormViewModel.screenState.value.addressDepartureCreated === null,
                                isDestinationCreatedError = searchFormViewModel.screenState.value.addressDestinationCreated === null,
                                isDepartureDateCreatedError = searchFormViewModel.screenState.value.dateDialogDepartureCreated === null,
                                isDepartureTimeCreatedError = searchFormViewModel.screenState.value.timeHourDepartureCreated === null,
                                isDestinationDateCreatedError = searchFormViewModel.screenState.value.dateDialogDestinationCreated === null,
                                isDestinationTimeCreatedError = searchFormViewModel.screenState.value.timeHourDestinationCreated === null
                            )
                        )

                        if (
                            searchFormViewModel.screenState.value.dateDialogDepartureCreated !== null
                            && searchFormViewModel.screenState.value.dateDialogDestinationCreated !== null
                            && searchFormViewModel.screenState.value.addressDepartureCreated !== null
                            && searchFormViewModel.screenState.value.addressDestinationCreated !== null
                            && searchFormViewModel.screenState.value.timeHourDepartureCreated !== null
                            && searchFormViewModel.screenState.value.timeMinuteDepartureCreated !== null
                            && searchFormViewModel.screenState.value.timeHourDestinationCreated !== null
                            && searchFormViewModel.screenState.value.timeMinuteDestinationCreated !== null
                        ) {
                            visiblePreviousForm.value = true
                            visibleCurrentForm.value = false
                        }
                    }) {
                    Icon(
                        imageVector = Icons.Outlined.NextPlan,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(id = R.string.next))
                }
            }
        }


        AnimatedVisibility(
            visible = visiblePreviousForm.value,
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
                Row(
                    Modifier.width(300.dp)
                ) {
                    IconButton(onClick = {
                        visiblePreviousForm.value = false
                        visibleCurrentForm.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .width(280.dp)
                        .border(border = if (searchFormViewModel.screenState.value.isPriceCreatedError) {
                            BorderStroke(1.dp, color = Color.Red)
                        } else {
                            BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary)
                        },
                        shape = RoundedCornerShape(30)),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    ),
                    shape = RoundedCornerShape(30),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Outlined.EuroSymbol,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    value = priceField,
                    onValueChange = { priceField = it },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.price),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                )

                if (searchFormViewModel.screenState.value.isPriceCreatedError) {
                    Text(
                        color = Color.Red,
                        text = stringResource(id = R.string.priceError)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .width(280.dp)
                        .border(border = if (searchFormViewModel.screenState.value.isNumberOfKgCreatedError) {
                            BorderStroke(1.dp, color = Color.Red)
                        } else {
                            BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary)
                        },
                        shape = RoundedCornerShape(30)),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    ),
                    shape = RoundedCornerShape(30),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = {
                        IconButton(onClick = { }) {
                            Image(
                                painter = painterResource(id = R.drawable.weight_fill0_wght400_grad0_opsz48),
                                contentDescription = "",
                                modifier = Modifier
                                    .height(24.dp)
                                    .width(24.dp),
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.tint(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    },
                    value = numberOfKgField,
                    onValueChange = { numberOfKgField = it },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.kilo_number),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                )

                if (searchFormViewModel.screenState.value.isNumberOfKgCreatedError) {
                    Text(
                        color = Color.Red,
                        text = stringResource(id = R.string.kilo_number_error)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .width(280.dp)
                        .border(border = if (searchFormViewModel.screenState.value.isMeetingPlace1CreatedError) {
                            BorderStroke(1.dp, color = Color.Red)
                        } else {
                            BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary)
                        }, shape = RoundedCornerShape(30)),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    ),
                    shape = RoundedCornerShape(30),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    leadingIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Outlined.LocationOn,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    value = meetingPlace1,
                    onValueChange = { meetingPlace1 = it },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.departure_local_meet),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                )

                if (searchFormViewModel.screenState.value.isMeetingPlace1CreatedError) {
                    Text(
                        color = Color.Red,
                        text = stringResource(id = R.string.departure_local_meet_error)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .width(280.dp)
                        .border(border = if (searchFormViewModel.screenState.value.isMeetingPlace2CreatedError) {
                            BorderStroke(1.dp, color = Color.Red)
                        } else {
                            BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary)
                        },
                        shape = RoundedCornerShape(30)),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    ),
                    shape = RoundedCornerShape(30),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    leadingIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Outlined.LocationOn,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    value = meetingPlace2,
                    onValueChange = { meetingPlace2 = it },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.arrival_local_meet),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                )

                if (searchFormViewModel.screenState.value.isMeetingPlace2CreatedError) {
                    Text(
                        color = Color.Red,
                        text = stringResource(id = R.string.arrival_local_meet_error)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    modifier = Modifier
                        .width(280.dp),
                    border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                    onClick = {

                        searchFormViewModel.onEvent(
                            SearchFormEvent.IsValidFinalFormStepp(
                                isPriceCreatedError = priceField.toFloat() <= 0,
                                isNumberOfKgCreatedError = numberOfKgField.toFloat() <= 0,
                                isMeetingPlace1CreatedError = meetingPlace1.isEmpty(),
                                isMeetingPlace2CreatedError = meetingPlace2.isEmpty()
                            )
                        )

                        if (
                            meetingPlace1.isEmpty()
                            && meetingPlace2.isEmpty()
                            && priceField.toFloat() <= 0
                            && numberOfKgField.toFloat() <= 0
                        ) {

                            searchFormViewModel.screenState.value.addressDestinationCreated?.id?.let {
                                searchFormViewModel.screenState.value.addressDepartureCreated?.id?.let { it1 ->
                                    screenState.userRoom[0].id?.let { it2 ->
                                        searchFormViewModel.screenState.value.dateDialogDepartureCreated?.let { it3 ->
                                            searchFormViewModel.screenState.value.timeHourDepartureCreated?.let { it4 ->
                                                searchFormViewModel.screenState.value.timeMinuteDepartureCreated?.let { it5 ->
                                                    util.getAnnouncementDate(
                                                        dateAnnouncement = it3,
                                                        hourAnnouncement = it4,
                                                        minuteAnnouncement = it5
                                                    )
                                                }
                                            }
                                        }?.let { it4 ->
                                            searchFormViewModel.screenState.value.dateDialogDestinationCreated?.let { it3 ->
                                                searchFormViewModel.screenState.value.timeHourDestinationCreated?.let { it5 ->
                                                    searchFormViewModel.screenState.value.timeMinuteDestinationCreated?.let { it6 ->
                                                        util.getAnnouncementDate(
                                                            dateAnnouncement = it3,
                                                            hourAnnouncement = it5,
                                                            minuteAnnouncement = it6
                                                        )
                                                    }
                                                }
                                            }?.let { it5 ->
                                                AnnouncementEvent.GenerateAnnouncementBody(
                                                    departureTime = it4,
                                                    arrivingTime = it5,
                                                    price = priceField.toFloat(),
                                                    meetingPlace1 = meetingPlace1,
                                                    meetingPlace2 = meetingPlace2,
                                                    user = it2,
                                                    destinationAddress = it,
                                                    departureAddress = it1,
                                                    numberOfKgs = NumberOfKgBody(
                                                        numberOfKg = numberOfKgField.toFloat(),
                                                        status = false
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                            }?.let {
                                announcementViewModel.onEvent(
                                    it
                                )

                                announcementViewModel.screenAnnouncementCreateScreenState.value.announcementBody?.let { it1 ->
                                    AnnouncementEvent.CreateAnnouncement(
                                        announcementBody = it1,
                                        token = screenState.tokenRoom[0].token
                                    )
                                }?.let { it2 ->
                                    announcementViewModel.onEvent(
                                        it2
                                    )
                                }
                            }
                        }

                    }) {
                    Icon(
                        imageVector = Icons.Outlined.SaveAs,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(id = R.string.publish))
                }

            }
        }


    }


    //departure Time Dialog
    if (departureTimeOpenDialog.value) {
        TimePickerDialog(
            title = if (departureTimeOpenDialog.value) {
                stringResource(id = R.string.select_time)
            } else {
                stringResource(id = R.string.enter_time)
            },
            onCancel = { departureTimeOpenDialog.value = false },
            onConfirm = {
                mDepartureTime.value = util.getTimeFormatter(
                    hour = departureTimePickerState.hour,
                    minute = departureTimePickerState.minute
                )
                departureTimeOpenDialog.value = false
                searchFormViewModel.screenState.value.timeHourDepartureCreated =
                    departureTimePickerState.hour
                searchFormViewModel.screenState.value.timeMinuteDepartureCreated =
                    departureTimePickerState.minute
                searchFormViewModel.screenState.value.isDepartureTimeCreatedError = false
            },
            toggle = {
                if (configuration.screenHeightDp > 400) {
                    // Make this take the entire viewport. This will guarantee that Screen readers
                    // focus the toggle first.

                    Box(
                        Modifier
                            .fillMaxSize()
                            .semantics { isContainer = true }
                    ) {
                        IconButton(
                            modifier = Modifier
                                // This is a workaround so that the Icon comes up first
                                // in the talkback traversal order. So that users of a11y
                                // services can use the text input. When talkback traversal
                                // order is customizable we can remove this.
                                .size(64.dp, 72.dp)
                                .align(Alignment.BottomStart)
                                .zIndex(5f),
                            onClick = {
                                departureTimeOpenDialog.value = !departureTimeOpenDialog.value
                            }) {

                            val icon = if (departureTimeOpenDialog.value) {
                                Icons.Outlined.Keyboard
                            } else {
                                Icons.Outlined.Schedule
                            }

                            Icon(
                                icon,
                                contentDescription = if (departureTimeOpenDialog.value) {
                                    "Switch to Text Input"
                                } else {
                                    "Switch to Touch Input"
                                }
                            )

                        }
                    }

                }

            }
        ) {
            if (departureTimeOpenDialog.value && configuration.screenHeightDp > 400) {
                TimePicker(state = departureTimePickerState)
            } else {
                TimeInput(state = departureTimePickerState)
            }

        }
    }


    //destination Time Dialog
    if (destinationTimeOpenDialog.value) {
        TimePickerDialog(
            title = if (destinationTimeOpenDialog.value) {
                stringResource(id = R.string.select_time)
            } else {
                stringResource(id = R.string.enter_time)
            },
            onCancel = { destinationTimeOpenDialog.value = false },
            onConfirm = {
                mDestinationTime.value = util.getTimeFormatter(
                    hour = destinationTimePickerState.hour,
                    minute = destinationTimePickerState.minute
                )
                destinationTimeOpenDialog.value = false
                searchFormViewModel.screenState.value.timeHourDestinationCreated =
                    destinationTimePickerState.hour
                searchFormViewModel.screenState.value.timeMinuteDestinationCreated =
                    destinationTimePickerState.minute
                searchFormViewModel.screenState.value.isDestinationTimeCreatedError = false
            },
            toggle = {
                if (configuration.screenHeightDp > 400) {
                    // Make this take the entire viewport. This will guarantee that Screen readers
                    // focus the toggle first.

                    Box(
                        Modifier
                            .fillMaxSize()
                            .semantics { isContainer = true }
                    ) {
                        IconButton(
                            modifier = Modifier
                                // This is a workaround so that the Icon comes up first
                                // in the talkback traversal order. So that users of a11y
                                // services can use the text input. When talkback traversal
                                // order is customizable we can remove this.
                                .size(64.dp, 72.dp)
                                .align(Alignment.BottomStart)
                                .zIndex(5f),
                            onClick = {
                                destinationTimeOpenDialog.value = !destinationTimeOpenDialog.value
                            }) {

                            val icon = if (destinationTimeOpenDialog.value) {
                                Icons.Outlined.Keyboard
                            } else {
                                Icons.Outlined.Schedule
                            }

                            Icon(
                                icon,
                                contentDescription = if (destinationTimeOpenDialog.value) {
                                    "Switch to Text Input"
                                } else {
                                    "Switch to Touch Input"
                                }
                            )

                        }
                    }

                }

            }
        ) {
            if (destinationTimeOpenDialog.value && configuration.screenHeightDp > 400) {
                TimePicker(state = destinationTimePickerState)
            } else {
                TimeInput(state = destinationTimePickerState)
            }

        }
    }

    //Departure Date Dialog
    if (departureDateOpenDialog.value) {
        val datePickerDeparturePickerState = rememberDatePickerState()
        val departureTimeConfirmEnabled =
            derivedStateOf { datePickerDeparturePickerState.selectedDateMillis != null }
        DatePickerDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                departureDateOpenDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        departureDateOpenDialog.value = false
                        val date =
                            datePickerDeparturePickerState.selectedDateMillis?.let { Date(it) }
                        mDepartureDate.value =
                            date?.let { util.getDateFormatter(it) }.toString()
                        searchFormViewModel.screenState.value.dateDialogDepartureCreated = date
                        searchFormViewModel.screenState.value.isDepartureDateCreatedError = false
                    },
                    enabled = departureTimeConfirmEnabled.value
                ) {
                    Text(text = stringResource(R.string.validate))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        departureDateOpenDialog.value = false
                    }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerDeparturePickerState)
        }
    }

    //Destination Date Dialog
    if (destinationDateOpenDialog.value) {
        val datePickerDestinationPickerState = rememberDatePickerState()
        val departureTimeConfirmEnabled =
            derivedStateOf { datePickerDestinationPickerState.selectedDateMillis != null }
        DatePickerDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                destinationDateOpenDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        destinationDateOpenDialog.value = false
                        val date =
                            datePickerDestinationPickerState.selectedDateMillis?.let { Date(it) }
                        mDestinationDate.value =
                            date?.let { util.getDateFormatter(it) }.toString()
                        searchFormViewModel.screenState.value.dateDialogDestinationCreated = date
                        searchFormViewModel.screenState.value.isDestinationDateCreatedError = false
                    },
                    enabled = departureTimeConfirmEnabled.value
                ) {
                    Text(text = stringResource(R.string.validate))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        destinationDateOpenDialog.value = false
                    }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerDestinationPickerState)
        }
    }

    LaunchedEffect(
        key1 = true
    ) {
        delay(3)
        visibleForm = true
    }
}