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
import com.android.yambasama.data.model.api.AnnouncementBody
import com.android.yambasama.domain.usecase.annoucement.CreateAnnouncementUseCase
import com.android.yambasama.domain.usecase.annoucement.GetAnnouncementUseCase
import com.android.yambasama.domain.usecase.annoucement.GetAnnouncementsUseCase
import com.android.yambasama.ui.UIEvent.Event.AnnouncementEvent
import com.android.yambasama.ui.UIEvent.ScreenState.AnnouncementState.AnnouncementCreateScreenState
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
    private val getAnnouncementUseCase: GetAnnouncementUseCase,
    private val createAnnouncementUseCase: CreateAnnouncementUseCase
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

    private val _screenAnnouncementBodyState = mutableStateOf(
        AnnouncementCreateScreenState(
            isNetworkError = false,
            isLoad = false,
            isDoneAnnouncementCreate = false
        )
    )

    val screenState: State<AnnouncementScreenState> = _screenState
    val screenAnnouncementState: State<AnnouncementDetailsScreenState> = _screenAnnouncementState
    val screenAnnouncementCreateScreenState: State<AnnouncementCreateScreenState> = _screenAnnouncementBodyState
    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()
    val currentPage : MutableState<Int> = mutableStateOf(1)

    private fun createAnnouncement(
        announcementBody: AnnouncementBody,
        token: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        if (isNetworkAvailable(app)) {
            try {
                _screenAnnouncementBodyState.value = _screenAnnouncementBodyState.value.copy(
                    isNetworkConnected = true,
                    isLoad = true,
                    isNetworkError = false,
                    isDoneAnnouncementCreate = false
                )

                val apiResult = createAnnouncementUseCase.execute(
                    announcementBody = announcementBody,
                    token = "Bearer $token"
                )

                apiResult.data?.let {
                    _screenAnnouncementBodyState.value = _screenAnnouncementBodyState.value.copy(
                        isNetworkConnected = true,
                        isLoad = false,
                        isNetworkError = false,
                        isDoneAnnouncementCreate = true
                    )
                    Log.d("MALEOMALEO9393MALEO9393", "MALEO-SAMA ${screenAnnouncementCreateScreenState.value.isDoneAnnouncementCreate}")
                }

            } catch (e: Exception) {
                _screenAnnouncementBodyState.value = _screenAnnouncementBodyState.value.copy(
                    isNetworkError = true,
                    isNetworkConnected = true,
                    isLoad = false,
                    isDoneAnnouncementCreate = false
                )
            }
        } else {
            _screenAnnouncementBodyState.value = _screenAnnouncementBodyState.value.copy(
                isNetworkConnected = false,
                isNetworkError = false,
                isLoad = false,
                isDoneAnnouncementCreate = false
            )
        }
    }


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
                    isLoad = true
                )
                getAnnouncements(
                    token = event.token,
                    arrivingTimeAfter = event.arrivingTimeAfter,
                    arrivingTimeBefore = event.arrivingTimeBefore,
                    destinationAddressId = event.destinationAddressId,
                    departureAddressId = event.departureAddressId
                )
            }

            is AnnouncementEvent.GenerateAnnouncementBody -> {
                val announcementBody = AnnouncementBody(
                    departureTime = event.departureTime,
                    arrivingTime = event.arrivingTime,
                    price = event.price,
                    meetingPlace1 = event.meetingPlace1,
                    meetingPlace2 = event.meetingPlace2,
                    user = event.user,
                    destinationAddress = event.destinationAddress,
                    departureAddress = event.departureAddress,
                    numberOfKgs = listOf(event.numberOfKgs)
                )
                _screenAnnouncementBodyState.value = _screenAnnouncementBodyState.value.copy(
                    announcementBody =  announcementBody
                )
            }

            is AnnouncementEvent.CreateAnnouncement -> {
                _screenAnnouncementBodyState.value = _screenAnnouncementBodyState.value.copy(
                    isLoad = true
                )

                createAnnouncement(
                    token = event.token,
                    announcementBody = event.announcementBody
                )
            }
            is AnnouncementEvent.AnnouncementDetails -> {
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
                            message = app.getString(R.string.empty_announcement_date)
                        )
                    )
                }
            }

            is AnnouncementEvent.IsCreateAnnouncementSuccess -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = app.getString(R.string.create_announcement_success)
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