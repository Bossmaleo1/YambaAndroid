package com.android.yambasama.presentation.viewModel.address

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.yambasama.data.model.dataRemote.Address
import com.android.yambasama.domain.usecase.address.GetAddressUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddressViewModel @Inject constructor(
    private val app: Application,
    private val getAddressUseCase: GetAddressUseCase
): AndroidViewModel(app) {

    private val addressList: MutableLiveData<List<Address>> = MutableLiveData()
    val addressListValue: LiveData<List<Address>> = addressList

    val addressStateRemoteList = mutableStateListOf<Address>()
    val currentPage : MutableState<Int> = mutableStateOf(1)

    init {

    }

    fun getAddress(
        isoCode: String,
        code: String,
        airportCode: String,
        airportName: String,
        townName: String,
        page: Int,
        pagination: Boolean,
        token: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = getAddressUseCase.execute(page, pagination, isoCode, code,airportCode, airportName, townName, "Bearer $token")
                    apiResult.data?.let {
                        addressList.postValue(it.address)
                        addressStateRemoteList.addAll(it.address)
                        currentPage.value = page
                        Log.d("MALEOMAYEKO9393", "${it.address}")
                    }
            } else {
                Toast.makeText(app.applicationContext,"Internet is not available", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Log.d("MALEO93AdressException", "${e.message}")
        }
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    fun initAddress() {
        addressStateRemoteList.removeAll(addressStateRemoteList)
    }
}