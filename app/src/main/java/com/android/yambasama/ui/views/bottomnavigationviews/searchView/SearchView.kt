package com.android.yambasama.ui.views.bottomnavigationviews

import android.R.attr
import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.yambasama.R
import com.android.yambasama.data.model.dataRemote.Address
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModel
import com.android.yambasama.ui.views.model.Route
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*


@ExperimentalMaterial3Api
@Composable
fun SearchView(
    navController: NavHostController,
    searchFormViewModel: SearchFormViewModel
) {
    // Fetching the Local Context
    val mContext = LocalContext.current
    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()
    var visibleForm by remember { mutableStateOf(false) }
    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }
    val formatter: SimpleDateFormat = SimpleDateFormat("EEE d MMM yy", Locale.getDefault())

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()
    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = formatter.format(Date(mYear,mMonth,mDayOfMonth))
        }, mYear, mMonth, mDay
    )
    val listCountries = Locale.getISOCountries()

    listCountries.forEach { country -> val locale = Locale(Locale.getDefault().isO3Language, country) }

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
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(30),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)) ,
                onClick = {
                    searchFormViewModel.screenState.value.departureOrDestination = 1
                    navController.navigate(Route.searchLocalizeView+"/${Route.homeNavParamDeparture}")
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
                                "${searchFormViewModel.screenState.value.addressDeparture?.townName}"
                            } else {
                                stringResource(R.string.departure)
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
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(30),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)) ,
                onClick = {
                    searchFormViewModel.screenState.value.departureOrDestination = 2
                    navController.navigate(Route.searchLocalizeView+"/${Route.homeNavParamDestination}")
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
                                "${searchFormViewModel.screenState.value.addressDestination?.townName}"
                            } else {
                                stringResource(R.string.destination)
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
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(30),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)) ,
                onClick = {
                    mDatePickerDialog.show()
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
                            text = if(mDate.value.length > 3) { mDate.value } else {stringResource(R.string.travel_date)},
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
                Text(stringResource(R.string.re_search))
            }
        }
    }

    LaunchedEffect(true) {
        delay(3)
        visibleForm = true
    }

}
