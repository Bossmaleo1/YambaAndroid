package com.android.yambasama.presentation.viewModel.announcement

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
import com.android.yambasama.R
import com.android.yambasama.domain.usecase.annoucement.GetAnnouncementsUseCase
import com.android.yambasama.ui.UIEvent.Event.AnnouncementEvent
import com.android.yambasama.ui.UIEvent.ScreenState.AnnouncementState.AnnouncementScreenState
import com.android.yambasama.ui.UIEvent.UIEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnnouncementViewModel @Inject constructor(
    private val app: Application,
    private val getAnnoucementsUseCase: GetAnnouncementsUseCase
) : AndroidViewModel(app) {

    private val _screenState = mutableStateOf(
        AnnouncementScreenState(
            announcementList = mutableListOf()
        )
    )
    val screenState: State<AnnouncementScreenState> = _screenState
    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    fun getAnnouncement(
        token: String,
        departureAddressId: Int,
        destinationAddressId: Int,
        departureTime: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        if (isNetworkAvailable(app)) {
            try {
                val apiResult =
                    getAnnoucementsUseCase.execute(
                        page = screenState.value.currentPage,
                        pagination = true,
                        departureTime = "",
                        departureAddress = "",
                        destinationAddress = "",
                        token = "Bearer $token"
                    )
                apiResult.data?.let { apiAnnouncementResponse ->
                    screenState.value.announcementList.addAll(apiAnnouncementResponse.annoucement)
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

    fun initAnnouncement() {
        screenState.value.announcementList.removeAll(screenState.value.announcementList)
    }

    fun onEvent(event: AnnouncementEvent) {
        when (event) {
            is AnnouncementEvent.AnnouncementInt -> {
                _screenState.value = _screenState.value.copy(
                    isLoad = true,
                    currentPage = 1,
                    announcementList = mutableListOf(),
                    initCall = 0,
                )
                getAnnouncement(
                    token = event.token,
                    departureTime = event.departureTime,
                    destinationAddressId = event.destinationAddressId,
                    departureAddressId = event.departureAddressId
                )
            }
            is AnnouncementEvent.InitAnnouncementState -> {

            }
            is AnnouncementEvent.ItemClicked -> {

            }
            is AnnouncementEvent.IsNetworkConnected -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = app.getString(R.string.network_error)
                        )
                    )
                }
            }
            is AnnouncementEvent.IsNetworkError -> {
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
}