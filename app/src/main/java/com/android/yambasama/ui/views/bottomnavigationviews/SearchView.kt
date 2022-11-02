package com.android.yambasama.ui.views.bottomnavigationviews

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.*
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.yambasama.R
import com.android.yambasama.ui.views.model.Route

@ExperimentalMaterial3Api
@Composable
fun SearchView() {
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
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            ),
            onValueChange = { departure = it },
            label = { Text("Departure") },
            placeholder = { Text("") },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.FlightTakeoff,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            modifier = Modifier
                .padding(top = 20.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = destination,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            ),
            onValueChange = { destination = it },
            label = { Text("Destination") },
            placeholder = { Text("") },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.FlightLand,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            modifier = Modifier
                .padding(top = 20.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = travelDate,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            ),
            onValueChange = { travelDate = it },
            label = { Text("Travel Date") },
            placeholder = { Text("") },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.Today,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            modifier = Modifier
                .padding(top = 20.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedButton(
            modifier = Modifier
                .width(280.dp)
                .padding(top = 30.dp),
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