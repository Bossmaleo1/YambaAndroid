package com.android.yambasama.ui.views.bottomnavigationviews

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.android.yambasama.R
import com.android.yambasama.ui.util.Util
import com.android.yambasama.ui.views.utils.TimePickerDialog
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterial3Api
@Composable
fun AddAdView(
    util: Util
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
    val mArrivingDate = remember { mutableStateOf("") }
    val formatter: SimpleDateFormat = SimpleDateFormat("EEE d MMM yy", Locale.getDefault())
    var visibleForm1 by remember { mutableStateOf(false) }
    var visibleForm2 by remember { mutableStateOf(false) }
    var showDepartureTimePicker by remember { mutableStateOf(false) }
    val timeformatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    val timeZoneId = TimeZone.getDefault().id


    AnimatedVisibility(
        visible = visibleForm1,
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
                            imageVector = Icons.Outlined.Today,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(
                            text = if (mDepartureDate.value.length > 3) {
                                mDepartureDate.value
                            } else {
                                stringResource(R.string.travel_date)
                            },
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(propagateMinConstraints = false) {
                Button(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = {
                       // mTimePickerDialog.show()
                        departureTimeOpenDialog.value = true
                    }
                ) { Text("Set Time") }
            }

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
                            imageVector = Icons.Outlined.Today,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(
                            text = if (mDepartureDate.value.length > 3) {
                                mDepartureDate.value
                            } else {
                                stringResource(R.string.travel_date)
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

                }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "Suivant")
            }
        }

        if (departureDateOpenDialog.value) {
            val departureTimePickerState = rememberDatePickerState()
            val departureTimeConfirmEnabled =
                derivedStateOf { departureTimePickerState.selectedDateMillis != null }
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
                            val date = departureTimePickerState.selectedDateMillis?.let { Date(it) }
                            mDepartureDate.value =
                                date?.let { util.getDateFormatter(it) }.toString()
                            //searchFormViewModel.screenState.value.dateDialog = date


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
                DatePicker(state = departureTimePickerState)
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

    }

    LaunchedEffect(true) {
        delay(3)
        visibleForm1 = true
    }

}