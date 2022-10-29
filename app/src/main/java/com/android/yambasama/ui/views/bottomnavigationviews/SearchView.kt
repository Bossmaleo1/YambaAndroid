package com.android.yambasama.ui.views.bottomnavigationviews

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FlightLand
import androidx.compose.material.icons.filled.FlightTakeoff
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
import androidx.navigation.NavHostController
import com.android.yambasama.R
import com.android.yambasama.ui.views.model.Route

@ExperimentalMaterial3Api
@Composable
fun SearchView(navController: NavHostController) {
    var departure by rememberSaveable { mutableStateOf("") }
    var destination by rememberSaveable { mutableStateOf("") }
    var travelDate by rememberSaveable { mutableStateOf("") }

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
                .padding(top = 20.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
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
                .padding(top = 20.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
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
                .padding(top = 20.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
            shape = RoundedCornerShape(12.dp)
        )

        Button(
            modifier = Modifier
                .width(280.dp)
                .padding(top = 20.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
            onClick = {
                navController.navigate(Route.announcement)
                // Toast.makeText(context,"MALEO MALEO MALEO",Toast.LENGTH_LONG).show()
                // Log.d("Test1", "Here");
            }) {
            Text("RECHERCHER", color = Color.White)
        }
    }
}