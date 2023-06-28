package com.android.yambasama.presentation.viewModel.searchForm

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.yambasama.R
import com.android.yambasama.ui.UIEvent.Event.AddressEvent
import com.android.yambasama.ui.UIEvent.Event.SearchFormEvent
import com.android.yambasama.ui.UIEvent.ScreenState.SearchFormState.SearchFormState
import com.android.yambasama.ui.UIEvent.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFormViewModel @Inject constructor(
    private val app: Application
): AndroidViewModel(app) {

    private val _screenState = mutableStateOf(
        SearchFormState(
            addressDeparture = null,
            addressDestination = null
        )
    )
    val screenState: State<SearchFormState> = _screenState
    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    fun onEvent(event: SearchFormEvent) {
        when (event) {
           is SearchFormEvent.SearchFormInit -> {
               _screenState.value = _screenState.value.copy(
                   addressDeparture = event.addressDeparture,
                   addressDestination = event.addressDestination
               )
           }

            is SearchFormEvent.IsTravelDateUpdated -> {
                _screenState.value = _screenState.value.copy(
                    isDepartureTimeError = event.isTravelDate,
                    isDepartureError =  event.isDeparture,
                    isDestinationError = event.isDestination
                )
            }
            is SearchFormEvent.IsTravelDateCreatedUpdated -> {
                _screenState.value = _screenState.value.copy(
                    isDepartureTimeCreatedError = event.isTravelDateCreated,
                    isDepartureCreatedError =  event.isDepartureCreated,
                    isDestinationCreatedError = event.isDestinationCreated
                )
            }
           is SearchFormEvent.SearchFormInitAddressDeparture -> {
               _screenState.value = _screenState.value.copy(
                   addressDeparture = event.addressDeparture
               )
           }

            is SearchFormEvent.SearchFormInitAddressDepartureCreated -> {
                _screenState.value = _screenState.value.copy(
                    addressDepartureCreated = event.addressDepartureCreated
                )
            }

           is SearchFormEvent.SearchFormInitAddressDestination -> {
               _screenState.value = _screenState.value.copy(
                   addressDestination = event.addressDestination
               )
           }
            is SearchFormEvent.SearchFormInitAddressDestinationCreated -> {
                _screenState.value = _screenState.value.copy(
                    addressDestinationCreated = event.addressDestinationCreated
                )
            }
            is SearchFormEvent.ErrorDestination -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = app.getString(R.string.form_destination_error)
                        )
                    )
                }
            }

            is SearchFormEvent.ErrorDestinationCreated -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = app.getString(R.string.form_destination_error)
                        )
                    )
                }
            }

            is SearchFormEvent.ErrorDeparture -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = app.getString(R.string.form_departure_error),
                        )
                    )
                }
            }

            is SearchFormEvent.ErrorDepartureCreated -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = app.getString(R.string.form_departure_error),
                        )
                    )
                }
            }
        }
    }
}