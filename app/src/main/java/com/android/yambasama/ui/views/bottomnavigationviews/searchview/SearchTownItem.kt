package com.android.yambasama.ui.views.bottomnavigationviews.searchview

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.yambasama.R
import com.android.yambasama.data.model.dataRemote.Address
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModel
import com.android.yambasama.ui.UIEvent.Event.SearchFormEvent
import com.android.yambasama.ui.util.Util
import com.android.yambasama.ui.views.model.Route


@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterial3Api
@Composable
fun SearchTownItem(
    navController: NavHostController,
    address: Address,
    searchFormViewModel: SearchFormViewModel,
    util: Util
) {
    val isDark = isSystemInDarkTheme()
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .run {
                if (
                    searchFormViewModel.screenState.value.departureOrDestination == 1
                    && searchFormViewModel.screenState.value.addressDestination?.id == address.id
                ) {
                    if (isDark) {
                        this.background(colorResource(R.color.GrayLight))
                    } else {
                        this.background(colorResource(R.color.GrayDark))
                    }
                } else if (
                    searchFormViewModel.screenState.value.departureOrDestination == 2
                    && searchFormViewModel.screenState.value.addressDeparture?.id == address.id
                ) {
                    if (isDark) {
                        this.background(colorResource(R.color.GrayLight))
                    } else {
                        this.background(colorResource(R.color.GrayDark))
                    }
                } else if (
                    searchFormViewModel.screenState.value.departureOrDestination == 3
                    && searchFormViewModel.screenState.value.addressDestinationCreated?.id == address.id
                ) {
                    if (isDark) {
                        this.background(colorResource(R.color.GrayLight))
                    } else {
                        this.background(colorResource(R.color.GrayDark))
                    }
                } else if (
                    searchFormViewModel.screenState.value.departureOrDestination == 4
                    && searchFormViewModel.screenState.value.addressDepartureCreated?.id == address.id
                ) {
                    if (isDark) {
                        this.background(colorResource(R.color.GrayLight))
                    } else {
                        this.background(colorResource(R.color.GrayDark))
                    }
                } else {
                    this.background(MaterialTheme.colorScheme.background)
                }
            },
        shape = RoundedCornerShape(corner = CornerSize(0.dp)),
        backgroundColor = MaterialTheme.colorScheme.background,
        onClick = {
            if (
                searchFormViewModel.screenState.value.departureOrDestination == 1
                && searchFormViewModel.screenState.value.addressDestination?.id == address.id
            ) {
                searchFormViewModel.onEvent(SearchFormEvent.ErrorDestination(context.getString(R.string.form_destination_error)))
            } else if (
                searchFormViewModel.screenState.value.departureOrDestination == 2
                && searchFormViewModel.screenState.value.addressDeparture?.id == address.id
            ) {
                searchFormViewModel.onEvent(SearchFormEvent.ErrorDeparture(context.getString(R.string.form_departure_error)))
            } else if (
                searchFormViewModel.screenState.value.departureOrDestination == 3
                && searchFormViewModel.screenState.value.addressDestinationCreated?.id == address.id
            ) {
                searchFormViewModel.onEvent(SearchFormEvent.ErrorDestinationCreated(context.getString(R.string.form_destination_error)))
            } else if (
                searchFormViewModel.screenState.value.departureOrDestination == 4
                && searchFormViewModel.screenState.value.addressDepartureCreated?.id == address.id
            ) {
                searchFormViewModel.onEvent(SearchFormEvent.ErrorDepartureCreated(context.getString(R.string.form_destination_error)))
            } else {

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
                } else if (searchFormViewModel.screenState.value.departureOrDestination == 3) {
                    searchFormViewModel.onEvent(
                        SearchFormEvent.SearchFormInitAddressDepartureCreated(
                            addressDepartureCreated = address
                        )
                    )
                } else if (searchFormViewModel.screenState.value.departureOrDestination == 4) {
                    searchFormViewModel.onEvent(
                        SearchFormEvent.SearchFormInitAddressDestinationCreated(
                            addressDestinationCreated = address
                        )
                    )
                }
                //We initialize our error display
                if (searchFormViewModel.screenState.value.departureOrDestination == 1) {
                    searchFormViewModel.onEvent(
                        SearchFormEvent.IsTravelDateUpdated(
                            isTravelDate = searchFormViewModel.screenState.value.isDepartureTimeError,
                            isDeparture = searchFormViewModel.screenState.value.addressDeparture === null,
                            isDestination = searchFormViewModel.screenState.value.isDestinationError
                        )
                    )
                } else if (searchFormViewModel.screenState.value.departureOrDestination == 2) {
                    searchFormViewModel.onEvent(
                        SearchFormEvent.IsTravelDateUpdated(
                            isTravelDate = searchFormViewModel.screenState.value.isDepartureTimeError,
                            isDeparture = searchFormViewModel.screenState.value.isDepartureError,
                            isDestination = searchFormViewModel.screenState.value.addressDestination === null
                        )
                    )
                } else if (searchFormViewModel.screenState.value.departureOrDestination == 3) {
                    searchFormViewModel.onEvent(
                        SearchFormEvent.IsTravelDateCreatedUpdated(
                            isTravelDateCreated = searchFormViewModel.screenState.value.isDepartureDateCreatedError,
                            isDepartureCreated = searchFormViewModel.screenState.value.addressDepartureCreated === null,
                            isDestinationCreated = searchFormViewModel.screenState.value.isDestinationCreatedError
                        )
                    )
                } else if (searchFormViewModel.screenState.value.departureOrDestination == 4) {
                    searchFormViewModel.onEvent(
                        SearchFormEvent.IsTravelDateCreatedUpdated(
                            isTravelDateCreated = searchFormViewModel.screenState.value.isDepartureDateCreatedError,
                            isDepartureCreated = searchFormViewModel.screenState.value.isDepartureError,
                            isDestinationCreated = searchFormViewModel.screenState.value.addressDestinationCreated === null
                        )
                    )
                }


                navController.navigate(Route.homeView)

            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .fillMaxWidth().run {
                    if (searchFormViewModel.screenState.value.departureOrDestination == 1
                        && searchFormViewModel.screenState.value.addressDestination?.id == address.id
                    ) {
                        if (isDark) {
                            this.background(colorResource(R.color.GrayLight))
                        } else {
                            this.background(colorResource(R.color.GrayDark))
                        }
                    } else if (searchFormViewModel.screenState.value.departureOrDestination == 2
                        && searchFormViewModel.screenState.value.addressDeparture?.id == address.id
                    ) {
                        if (isDark) {
                            this.background(colorResource(R.color.GrayLight))
                        } else {
                            this.background(colorResource(R.color.GrayDark))
                        }
                    } else if (searchFormViewModel.screenState.value.departureOrDestination == 3
                        && searchFormViewModel.screenState.value.addressDestinationCreated?.id == address.id
                    ) {
                        if (isDark) {
                            this.background(colorResource(R.color.GrayLight))
                        } else {
                            this.background(colorResource(R.color.GrayDark))
                        }
                    } else if (searchFormViewModel.screenState.value.departureOrDestination == 4
                        && searchFormViewModel.screenState.value.addressDepartureCreated?.id == address.id
                    ) {
                        if (isDark) {
                            this.background(colorResource(R.color.GrayLight))
                        } else {
                            this.background(colorResource(R.color.GrayDark))
                        }
                    } else {
                        this.background(MaterialTheme.colorScheme.background)
                    }
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(18.dp).run {
                        if (searchFormViewModel.screenState.value.departureOrDestination == 1
                            && searchFormViewModel.screenState.value.addressDestination?.id == address.id
                        ) {
                            if (isDark) {
                                this.background(colorResource(R.color.GrayLight))
                            } else {
                                this.background(colorResource(R.color.GrayDark))
                            }
                        } else if (searchFormViewModel.screenState.value.departureOrDestination == 2
                            && searchFormViewModel.screenState.value.addressDeparture?.id == address.id
                        ) {
                            if (isDark) {
                                this.background(colorResource(R.color.GrayLight))
                            } else {
                                this.background(colorResource(R.color.GrayDark))
                            }
                        } else if (searchFormViewModel.screenState.value.departureOrDestination == 3
                            && searchFormViewModel.screenState.value.addressDestinationCreated?.id == address.id
                        ) {
                            if (isDark) {
                                this.background(colorResource(R.color.GrayLight))
                            } else {
                                this.background(colorResource(R.color.GrayDark))
                            }
                        } else if (searchFormViewModel.screenState.value.departureOrDestination == 4
                            && searchFormViewModel.screenState.value.addressDepartureCreated?.id == address.id
                        ) {
                            if (isDark) {
                                this.background(colorResource(R.color.GrayLight))
                            } else {
                                this.background(colorResource(R.color.GrayDark))
                            }
                        } else {
                            this.background(Color.Transparent)
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
                    text = "${address.townName} (${util.getCountry(address.code)} (${address.airportName}, ${address.airportCode}))",
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
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