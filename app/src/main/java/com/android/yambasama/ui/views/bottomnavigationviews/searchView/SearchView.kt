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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.yambasama.R
import com.android.yambasama.ui.views.model.Route
import kotlinx.coroutines.delay

@ExperimentalMaterial3Api
@Composable
fun SearchView(navController: NavHostController) {
    var departure by rememberSaveable { mutableStateOf("") }
    var destination by rememberSaveable { mutableStateOf("") }
    var travelDate by rememberSaveable { mutableStateOf("") }
    var enabled by remember { mutableStateOf(false) }

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
            onValueChange = {
                departure = it
            },
            label = { Text(stringResource(id = R.string.departure)) },
            placeholder = { Text("") },
            enabled = enabled,
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
                .clickable {
                    navController.navigate(Route.searchLocalizeView)
                    Log.d("MALEO9393", "MALEO Test")
                }
                .padding(top = 0.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = destination,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            ),
            onValueChange = { destination = it },
            label = { Text(stringResource(id = R.string.destination)) },
            placeholder = { Text("") },
            //enabled = false,
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
            label = { Text(stringResource(id = R.string.travel_date)) },
            placeholder = { Text("") },
            //enabled = false,
            leadingIcon = {
                IconButton(onClick = {

                }) {
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