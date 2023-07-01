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
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModel
import com.android.yambasama.ui.util.Util
import com.android.yambasama.ui.views.model.Route
import com.android.yambasama.ui.views.utils.TimePickerDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterial3Api
@Composable
fun AddAdView(
    util: Util,
    navController: NavHostController,
    visibleCurrentForm: MutableState<Boolean>,
    visiblePreviousForm: MutableState<Boolean>,
    searchFormViewModel: SearchFormViewModel
) {

    val departureDateOpenDialog = remember { mutableStateOf(false) }
    val departureTimeOpenDialog = remember { mutableStateOf(false) }
    val departureTimePickerState = rememberTimePickerState(
        initialHour = 12
    )
    val arrivingTimeOpenDialog = remember { mutableStateOf(false) }
    val timeFormatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    val arrivingTimePickerState = rememberDatePickerState()
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
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

    var priceField by remember { mutableStateOf("") }
    var numberOfKgField by remember { mutableStateOf("") }
    var meetingPlace1 by remember { mutableStateOf("") }
    var meetingPlace2 by remember { mutableStateOf("") }

    var visibleForm by remember { mutableStateOf(false) }


    if (searchFormViewModel.screenState.value.dateDialogDepartureCreated !== null) {
        mDepartureDate.value = formatter.format(searchFormViewModel.screenState.value.dateDialogDepartureCreated!!)
    }

    if (searchFormViewModel.screenState.value.dateDialogDestinationCreated !== null) {
        mDestinationDate.value = formatter.format(searchFormViewModel.screenState.value.dateDialogDestinationCreated!!)
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
                                    "${searchFormViewModel.screenState.value.addressDepartureCreated?.townName} ( ${util.getCountry(searchFormViewModel.screenState.value.addressDepartureCreated!!.code)} ( ${searchFormViewModel.screenState.value.addressDepartureCreated?.airportName}, ${searchFormViewModel.screenState.value.addressDepartureCreated?.airportCode} ))"
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
                                    "${searchFormViewModel.screenState.value.addressDestinationCreated?.townName} ( ${util.getCountry(searchFormViewModel.screenState.value.addressDestinationCreated!!.code)} ( ${searchFormViewModel.screenState.value.addressDestinationCreated?.airportName}, ${searchFormViewModel.screenState.value.addressDestinationCreated?.airportCode} ))"
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
                                    "Date de départ"
                                },
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    modifier = Modifier
                        .width(280.dp)
                        .height(55.dp),
                    /*border = if (searchFormViewModel.screenState.value.isDepartureTimeError) {
                        BorderStroke(1.dp, color = Color.Red)
                    } else {
                        BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary)
                    },*/
                    shape = RoundedCornerShape(30),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.2f
                        )
                    ),
                    onClick = {
                        //departureDateOpenDialog.value = true
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
                                    "Heure de départ"
                                },
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    modifier = Modifier
                        .width(280.dp)
                        .height(55.dp),
                    /*border = if (searchFormViewModel.screenState.value.isDepartureTimeError) {
                        BorderStroke(1.dp, color = Color.Red)
                    } else {
                        BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary)
                    },*/
                    shape = RoundedCornerShape(30),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.2f
                        )
                    ),
                    onClick = {
                        //departureDateOpenDialog.value = true
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
                                    "Date d'arrivée"
                                },
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    modifier = Modifier
                        .width(280.dp)
                        .height(55.dp),
                    /*border = if (searchFormViewModel.screenState.value.isDepartureTimeError) {
                        BorderStroke(1.dp, color = Color.Red)
                    } else {
                        BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary)
                    },*/
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
                                imageVector = Icons.Outlined.Timer,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text(
                                text = if (mDestinationTime.value.length > 3) {
                                    mDestinationTime.value
                                } else {
                                    "Heure d'arrivée"
                                },
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    modifier = Modifier
                        .width(280.dp),
                    border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                    onClick = {
                        visiblePreviousForm.value = true
                        visibleCurrentForm.value = false
                    }) {
                    Icon(
                        imageVector = Icons.Outlined.NextPlan,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = "Suivant")
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
                        .width(280.dp),
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
                        Text("Prix")
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .width(280.dp),
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
                    value = priceField,
                    onValueChange = { priceField = it },
                    placeholder = {
                        Text("Nombre de Kg")
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .width(280.dp),
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
                    value = priceField,
                    onValueChange = { priceField = it },
                    placeholder = {
                        Text("Lieux de rencontre au départ")
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .width(280.dp),
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
                    value = priceField,
                    onValueChange = { priceField = it },
                    placeholder = {
                        Text("Lieux de rencontre à l'arrivée")
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    modifier = Modifier
                        .width(280.dp),
                    border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                    onClick = {

                    }) {
                    Icon(
                        imageVector = Icons.Outlined.SaveAs,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = "Publier")
                }

            }
        }




    }


    if (departureTimeOpenDialog.value) {
        TimePickerDialog(
            title = if (departureTimeOpenDialog.value) { "Select Time " } else { "Enter Time" },
            onCancel = { departureTimeOpenDialog.value = false },
            onConfirm = {
                val cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY, departureTimePickerState.hour)
                cal.set(Calendar.MINUTE, departureTimePickerState.minute)
                cal.isLenient = false
                snackScope.launch {
                    snackState.showSnackbar("Entered time: ${formatter.format(cal.time)}")
                }
                departureTimeOpenDialog.value = false
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
                            onClick = { departureTimeOpenDialog.value = !departureTimeOpenDialog.value }) {

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
                        val date = datePickerDeparturePickerState.selectedDateMillis?.let { Date(it) }
                        mDepartureDate.value =
                            date?.let { util.getDateFormatter(it) }.toString()
                        searchFormViewModel.screenState.value.dateDialogDepartureCreated = date


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

    LaunchedEffect(
        key1 = true
    ) {
        delay(3)
        visibleForm = true
    }
}