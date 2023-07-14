package com.android.yambasama.presentation.viewModel.address

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.yambasama.data.model.dataRemote.Address
import com.android.yambasama.domain.usecase.address.GetAddressUseCase
import com.android.yambasama.ui.UIEvent.Event.AddressEvent
import com.android.yambasama.ui.UIEvent.ScreenState.AddressScreenState.AddressScreenState
import com.android.yambasama.ui.UIEvent.UIEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import com.android.yambasama.R
import java.util.Locale
import javax.inject.Inject

class AddressViewModel @Inject constructor(
    private val app: Application,
    private val getAddressUseCase: GetAddressUseCase
) : AndroidViewModel(app) {

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
        if (isNetworkAvailable(app)) {
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
                    isNetworkError = false,
                    initCall = screenState.value.initCall++
                )
            } catch (e: Exception) {
                _screenState.value = _screenState.value.copy(
                    isNetworkError = true,
                    isNetworkConnected = true,
                    isLoad = false
                )
            }
        } else {
            _screenState.value = _screenState.value.copy(
                isNetworkConnected = false,
                isNetworkError = false,
                isLoad = false
            )
        }
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network =
                connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
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
                    addressListTemp = mutableListOf()
                )
                getAddress(
                    token = event.token,
                    locale = event.locale
                )
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
                    addressList = mutableListOf(),
                    addressListTemp = mutableListOf(),
                    searchInputValue = ""
                )
            }
            is AddressEvent.IsNetworkConnected -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = app.getString(R.string.network_error)
                        )
                    )
                }
            }
            is AddressEvent.IsNetworkError -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = app.getString(R.string.is_connect_error)
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