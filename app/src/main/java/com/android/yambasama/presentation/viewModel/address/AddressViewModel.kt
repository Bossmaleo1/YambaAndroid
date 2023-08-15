package com.android.yambasama.presentation.viewModel.address


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.yambasama.data.model.dataRemote.Address
import com.android.yambasama.domain.usecase.address.GetAddressUseCase
import com.android.yambasama.presentation.util.isNetworkAvailable
import com.android.yambasama.ui.UIEvent.Event.AddressEvent
import com.android.yambasama.ui.UIEvent.ScreenState.AddressScreenState.AddressScreenState
import com.android.yambasama.ui.UIEvent.UIEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val getAddressUseCase: GetAddressUseCase
) : ViewModel() {

    private val _screenState = mutableStateOf(
        AddressScreenState(
            addressList = mutableListOf(),
            searchInputValue = "",
            locale = "${Locale.getDefault().language}"
        )
    )
    val screenState: State<AddressScreenState> = _screenState
    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    fun getAddress(
        locale: String,
        token: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val apiResult =
                getAddressUseCase.execute(
                    locale = locale,
                    page = screenState.value.currentPage,
                    query = screenState.value.searchInputValue,
                    token = "Bearer $token"
                )
            apiResult.data?.let { apiAddressResponse ->
                getAdressResult(apiAddressResponse)
            }

            _screenState.value = _screenState.value.copy(
                isNetworkConnected = true,
                isLoad = false,
                isInternalError = ((apiResult.message).equals("Internal Server Error")),
                isNetworkError = false,
                initCall = screenState.value.initCall++
            )
        } catch (e: Exception) {
            _screenState.value = _screenState.value.copy(
                isNetworkError = true,
                isInternalError = false,
                isNetworkConnected = true,
                isLoad = false
            )
        }
    }

    fun initAddress() {
        _screenState.value = _screenState.value.copy(
            addressList = mutableListOf()
        )
    }

    fun onEvent(event: AddressEvent) {
        when (event) {
            is AddressEvent.SearchValueEntered -> {
                _screenState.value = _screenState.value.copy(
                    searchInputValue = event.value,
                    isLoad = true,
                    currentPage = 1,
                    addressList = mutableListOf()
                )
                getAddress(
                    token = event.token,
                    locale = event.locale
                )

                if (event.value.isEmpty()) {
                    _screenState.value = _screenState.value.copy(
                        addressList = mutableListOf(),
                        addressListTemp = mutableListOf()
                    )
                }
            }

            is AddressEvent.AddressInit -> {
                _screenState.value = _screenState.value.copy(
                    isLoad = true,
                    searchInputValue = "",
                    currentPage = 1,
                    addressList = mutableListOf(),
                    addressListTemp = mutableListOf(),
                    isInternalError = false
                )

                if (isNetworkAvailable(event.app)) {
                    getAddress(
                        token = event.token,
                        locale = event.locale
                    )
                } else {
                    _screenState.value = _screenState.value.copy(
                        isNetworkConnected = false,
                        isNetworkError = false,
                        isLoad = false,
                        isInternalError = false,
                    )
                }
            }

            is AddressEvent.ItemClicked -> {

            }

            is AddressEvent.InitAddressState -> {
                _screenState.value = _screenState.value.copy(
                    isNetworkConnected = true,
                    isLoad = false,
                    isNetworkError = false,
                    currentPage = 1,
                    initCall = 0,
                    isInternalError = false,
                    addressList = mutableListOf(),
                    addressListTemp = mutableListOf(),
                    searchInputValue = ""
                )
            }

            is AddressEvent.IsInternalError -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = "Internal Error, Error 500"
                        )
                    )
                }
            }

            is AddressEvent.IsNetworkConnected -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = event.errorMessage
                        )
                    )
                }
            }

            is AddressEvent.IsNetworkError -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = event.errorMessage
                        )
                    )
                }
            }

        }
    }


    private fun getAdressResult(address: List<Address>) {
        if (screenState.value.addressList.isEmpty()) {
            screenState.value.addressList = address
        }
        if (screenState.value.addressListTemp.isEmpty()) {
            screenState.value.addressListTemp = address
        }
    }


}