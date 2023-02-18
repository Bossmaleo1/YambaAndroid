package com.android.yambasama.ui.views.bottomnavigationviews.searchView

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import com.android.yambasama.data.model.dataRemote.Address
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModel
import com.android.yambasama.ui.UIEvent.Event.SearchFormEvent
import com.android.yambasama.ui.views.model.Route
import java.util.*


@ExperimentalMaterial3Api
@Composable
fun SearchTownItem(
    //onNavigateToHomeScreen: (String)->Unit,
    address: Address,
    searchFormViewModel: SearchFormViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(corner = CornerSize(0.dp)),
        onClick = {
            /*onNavigateToHomeScreen(
                "${address.id};${address.isoCode};${address.code};${address.airportName};${address.airportCode};${address.townName};${origin}"
            )*/
            if (searchFormViewModel.screenState.value.departureOrDestination == 1) {
                searchFormViewModel.onEvent(
                    SearchFormEvent.SearchFormInitAddressDeparture(
                        addressDeparture = address
                    )
                )
            } else if (searchFormViewModel.screenState.value.departureOrDestination == 2) {
                searchFormViewModel.onEvent(
                    SearchFormEvent.SearchFormInitAddressDestination(
                        addressDestination = address
                    )
                )
            }
        }
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "${address.townName} (${address.airportName} (${address.airportCode}) / ${address.code}",
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }

        Divider(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(0.20.dp),
        )
    }
}