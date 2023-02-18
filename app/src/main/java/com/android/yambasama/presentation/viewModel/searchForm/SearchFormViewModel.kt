package com.android.yambasama.presentation.viewModel.searchForm

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.android.yambasama.ui.UIEvent.Event.SearchFormEvent
import com.android.yambasama.ui.UIEvent.ScreenState.SearchFormState.SearchFormState
import com.android.yambasama.ui.UIEvent.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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
        Log.d("MALEODEPARTURE1993", "Test Test")
        when (event) {
           is SearchFormEvent.SearchFormInit -> {
               _screenState.value = _screenState.value.copy(
                   addressDeparture = event.addressDeparture,
                   addressDestination = event.addressDestination
               )
           }
           is SearchFormEvent.SearchFormInitAddressDeparture -> {
               _screenState.value = _screenState.value.copy(
                   addressDeparture = event.addressDeparture
               )
           }
           is SearchFormEvent.SearchFormInitAddressDestination -> {
               _screenState.value = _screenState.value.copy(
                   addressDestination = event.addressDestination
               )
           }
        }
    }
}