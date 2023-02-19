package com.android.yambasama.ui.views.bottomnavigationviews.searchView

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.yambasama.data.model.dataRemote.Address
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModel
import com.android.yambasama.ui.UIEvent.Event.SearchFormEvent
import com.android.yambasama.ui.views.model.Route


@ExperimentalMaterial3Api
@Composable
fun SearchTownItem(
    navController: NavHostController,
    address: Address,
    searchFormViewModel: SearchFormViewModel
) {
    /*var myCardModifer = Modifier
        .fillMaxWidth()
        .wrapContentHeight().background(Color.White)*//*if (searchFormViewModel.screenState.value.departureOrDestination === 1) {
        return Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(Color.White)
    } else {

    }*/
    /*var myColumnModifier = Modifier.fillMaxWidth()
        .wrapContentHeight().background(Color.White)
    var myRowModifier = Modifier
        .fillMaxSize()
        .wrapContentHeight()
        .padding(18.dp).background(Color.White)*/

    /*myCardModifer.then(Modifier.background(Color.Red))
    myColumnModifier.then(Modifier.background(Color.Red))
    myRowModifier.then(Modifier.background(Color.Red))

    if (searchFormViewModel.screenState.value.departureOrDestination == 1 && searchFormViewModel.screenState.value.addressDestination === address) {
        myCardModifer.then(Modifier.background(Color.White))
        myColumnModifier.then(Modifier.background(Color.White))
        myRowModifier.then(Modifier.background(Color.White))
    } else if (searchFormViewModel.screenState.value.departureOrDestination == 2 && searchFormViewModel.screenState.value.addressDeparture === address){
        myCardModifer.then(Modifier.background(Color.White))
    }*/



    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .run {
                if (
                    searchFormViewModel.screenState.value.departureOrDestination == 1
                    && searchFormViewModel.screenState.value.addressDestination?.id == address.id
                ) {
                    this.background(Color.Gray)
                } else if (
                    searchFormViewModel.screenState.value.departureOrDestination == 2
                    && searchFormViewModel.screenState.value.addressDeparture?.id == address.id
                ) {
                    this.background(Color.Gray)
                } else {
                    this.background(Color.White)
                }
            },
        shape = RoundedCornerShape(corner = CornerSize(0.dp)),
        onClick = {
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
            navController.navigate(Route.homeView)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight().run {
                    if (searchFormViewModel.screenState.value.departureOrDestination == 1
                        && searchFormViewModel.screenState.value.addressDestination?.id == address.id
                    ) {
                        this.background(Color.Gray)
                    } else if (searchFormViewModel.screenState.value.departureOrDestination == 2
                        && searchFormViewModel.screenState.value.addressDeparture?.id == address.id
                    ) {
                        this.background(Color.Gray)
                    } else {
                        this.background(Color.White)
                    }
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight()
                    .padding(18.dp).run {
                        if (searchFormViewModel.screenState.value.departureOrDestination == 1
                            && searchFormViewModel.screenState.value.addressDestination?.id == address.id
                        ) {
                            this.background(Color.Gray)
                        } else if (searchFormViewModel.screenState.value.departureOrDestination == 2
                            && searchFormViewModel.screenState.value.addressDeparture?.id == address.id
                        ) {
                            this.background(Color.Gray)
                        } else {
                            this.background(Color.White)
                        }
                    },
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "${address.airportName} ( ${address.townName} )",
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleSmall
                )
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
}