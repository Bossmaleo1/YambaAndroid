package com.android.yambasama.ui.views.bottomnavigationviews

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.yambasama.R
import com.android.yambasama.ui.views.model.Route
import kotlinx.coroutines.delay
import android.widget.DatePicker
import android.app.DatePickerDialog
import java.util.*

@ExperimentalMaterial3Api
@Composable
fun SearchView(navController: NavHostController) {
    var departure by rememberSaveable { mutableStateOf("") }
    var destination by rememberSaveable { mutableStateOf("") }
    var travelDate by rememberSaveable { mutableStateOf("") }
    var enabled by remember { mutableStateOf(false) }
    // Fetching the Local Context
    val mContext = LocalContext.current
    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )

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
                navController.navigate(Route.searchLocalizeView)
            }) {
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
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
                        text = if(departure.length > 3) { departure } else {stringResource(R.string.departure)},
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
                navController.navigate(Route.searchLocalizeView)
            }) {
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
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
                        text = if(departure.length > 3) { departure } else {stringResource(R.string.destination)},
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
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
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
                //navController.navigate(Route.homeView)
                // Toast.makeText(context,"MALEO MALEO MALEO",Toast.LENGTH_LONG).show()
                // Log.d("Test1", "Here");
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