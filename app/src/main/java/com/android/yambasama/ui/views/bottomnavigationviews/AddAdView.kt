package com.android.yambasama.ui.views.bottomnavigationviews

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AlarmOn
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FlightLand
import androidx.compose.material.icons.outlined.FlightTakeoff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.android.yambasama.R

@ExperimentalMaterial3Api
@Composable
fun AddAdView() {
    var departure by rememberSaveable { mutableStateOf("") }
    var destination by rememberSaveable { mutableStateOf("") }
    var travelDate by rememberSaveable { mutableStateOf("") }
    var travelTime by rememberSaveable { mutableStateOf("") }
    var arriveTime by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        OutlinedTextField(
            value = departure,
            onValueChange = { departure = it },
            label = { Text("Departure") },
            placeholder = { Text("") },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.FlightTakeoff,
                        contentDescription = "",
                        tint = colorResource(R.color.Purple700)
                    )
                }
            },
            modifier = Modifier
                .padding(top = 10.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("Destination") },
            placeholder = { Text("") },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.FlightLand,
                        contentDescription = "",
                        tint = colorResource(R.color.Purple700)
                    )
                }
            },
            modifier = Modifier
                .padding(top = 10.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = travelDate,
            onValueChange = { travelDate = it },
            label = { Text("Travel Date") },
            placeholder = { Text("") },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "",
                        tint = colorResource(R.color.Purple700)
                    )
                }
            },
            modifier = Modifier
                .padding(top = 10.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = travelDate,
            onValueChange = { travelDate = it },
            label = { Text("Departure Time") },
            placeholder = { Text("") },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.AlarmOn,
                        contentDescription = "",
                        tint = colorResource(R.color.Purple700)
                    )
                }
            },
            modifier = Modifier
                .padding(top = 10.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = travelDate,
            onValueChange = { travelDate = it },
            label = { Text("Arrive Time") },
            placeholder = { Text("") },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "",
                        tint = colorResource(R.color.Purple700)
                    )
                }
            },
            modifier = Modifier
                .padding(top = 10.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
            shape = RoundedCornerShape(12.dp)
        )

        Button(
            modifier = Modifier
                .width(280.dp)
                .padding(top = 10.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
            onClick = {
                //Here our click event
            }) {
            Text("SUIVANT", color = Color.White)
        }
    }
}