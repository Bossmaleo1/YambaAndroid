package com.android.yambasama.presentation.viewModel.announcement

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.yambasama.R
import com.android.yambasama.domain.usecase.annoucement.GetAnnouncementUseCase
import com.android.yambasama.domain.usecase.annoucement.GetAnnouncementsUseCase
import com.android.yambasama.ui.UIEvent.Event.AnnouncementEvent
import com.android.yambasama.ui.UIEvent.ScreenState.AnnouncementState.AnnouncementDetailsScreenState
import com.android.yambasama.ui.UIEvent.ScreenState.AnnouncementState.AnnouncementScreenState
import com.android.yambasama.ui.UIEvent.UIEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnnouncementViewModel @Inject constructor(
    private val app: Application,
    private val getAnnoucementsUseCase: GetAnnouncementsUseCase,
    private val getAnnouncementUseCase: GetAnnouncementUseCase
) : AndroidViewModel(app) {

    private val _screenState = mutableStateOf(
        AnnouncementScreenState(
            announcementList = mutableStateListOf()
        )
    )

    private val _screenAnnouncementState = mutableStateOf(
        AnnouncementDetailsScreenState(
            announcementDetails = null
        )
    )
    val screenState: State<AnnouncementScreenState> = _screenState
    val screenAnnouncementState: State<AnnouncementDetailsScreenState> = _screenAnnouncementState
    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()
    val currentPage : MutableState<Int> = mutableStateOf(1)

    fun getAnnouncements(
        token: String,
        departureAddressId: Int,
        destinationAddressId: Int,
        arrivingTimeAfter: String,
        arrivingTimeBefore: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        if (isNetworkAvailable(app)) {
            try {
                _screenState.value = _screenState.value.copy(
                    isNetworkConnected = true,
                    isLoad = true,
                    isNetworkError = false,
                    initCall = screenState.value.initCall++,
                    // currentPage = screenState.value.currentPage++
                )
                val apiResult =
                    getAnnoucementsUseCase.execute(
                        page = screenState.value.currentPage,
                        pagination = true,
                        arrivingTimeAfter = arrivingTimeAfter,
                        arrivingTimeBefore = arrivingTimeBefore,
                        departureAddress = departureAddressId,
                        destinationAddress = destinationAddressId,
                        token = "Bearer $token"
                    )
                apiResult.data?.let { apiAnnouncementResponse ->
                    screenState.value.announcementList.addAll(apiAnnouncementResponse)
                    screenState.value.refreshing = false
                    if (apiAnnouncementResponse.isEmpty()) {
                        _screenState.value = _screenState.value.copy(
                            isEmptyAnnouncement = true,
                            isLoad = false,
                        )
                    } else
                      if(apiAnnouncementResponse.size < 10) {
                        _screenState.value = _screenState.value.copy(
                            isNetworkConnected = true,
                            isLoad = false,
                            isNetworkError = false,
                            initCall = screenState.value.initCall++,
                            refreshing = false,
                            isEmptyAnnouncement = false
                            // currentPage = screenState.value.currentPage++
                        )
                    }
                }


            } catch (e: Exception) {
                _screenState.value = _screenState.value.copy(
                    isNetworkError = true,
                    isNetworkConnected = true,
                    isLoad = false,
                    refreshing = false
                )
            }
        } else {
            _screenState.value = _screenState.value.copy(
                isNetworkConnected = false,
                isNetworkError = false,
                isLoad = false,
                refreshing = false
            )
        }
    }

    fun getAnnouncement(
        id: Int,
        token: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("MALEO9393TESTTEST9393", "MALEO Testing")
        if (isNetworkAvailable(app)) {
            try {
                _screenAnnouncementState.value = _screenAnnouncementState.value.copy(
                    isNetworkConnected = true,
                    isLoad = true,
                    isNetworkError = false,
                )

                val apiResult = getAnnouncementUseCase.execute(id = id,token = "Bearer $token")
                apiResult.data?.let { apiAnnouncementResponse ->
                    screenAnnouncementState.value.announcementDetails = apiAnnouncementResponse
                    screenAnnouncementState.value.refreshing = false

                    _screenAnnouncementState.value = _screenAnnouncementState.value.copy(
                        isLoad = false
                    )
                }
            } catch (e: Exception) {
                _screenAnnouncementState.value = _screenAnnouncementState.value.copy(
                    isNetworkError = true,
                    isNetworkConnected = true,
                    isLoad = false
                )
            }

        } else {
            _screenAnnouncementState.value = _screenAnnouncementState.value.copy(
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
                    /*currentPage = 1,
                    announcementList = mutableStateListOf(),
                    initCall = 0,*/
                )
                getAnnouncements(
                    token = event.token,
                    arrivingTimeAfter = event.arrivingTimeAfter,
                    arrivingTimeBefore = event.arrivingTimeBefore,
                    destinationAddressId = event.destinationAddressId,
                    departureAddressId = event.departureAddressId
                )
            }
            is AnnouncementEvent.AnnouncementDetails -> {
                Log.d("MALEO9393TESTTEST9393", "MALEO Testing exterieur")
                _screenAnnouncementState.value = _screenAnnouncementState.value.copy(
                    isLoad = true
                )

                getAnnouncement(
                    token = event.token,
                    id = event.id
                )
            }
            is AnnouncementEvent.InitAnnouncementState -> {

            }
            is AnnouncementEvent.ItemClicked -> {
                _screenState.value = _screenState.value.copy(
                    announcement = mutableListOf(event.announcement)
                )
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

            is AnnouncementEvent.IsEmptyAnnouncement -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = "Il y a aucune annonce pour cette date"
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
    }
}