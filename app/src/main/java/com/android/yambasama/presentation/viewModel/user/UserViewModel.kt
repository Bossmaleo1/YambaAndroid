package com.android.yambasama.presentation.viewModel.user

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.android.yambasama.data.db.dataStore.TokenManager
import com.android.yambasama.data.model.dataLocal.UserRoom
import com.android.yambasama.data.model.dataRemote.Token
import com.android.yambasama.domain.usecase.user.*
import com.android.yambasama.presentation.util.isNetworkAvailable
import com.android.yambasama.ui.UIEvent.Event.AuthEvent
import com.android.yambasama.ui.UIEvent.ScreenState.AuthScreenState.AuthScreenState
import com.android.yambasama.ui.UIEvent.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val getSavedUserUseCase: GetSavedUserUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _screenState = mutableStateOf(
        AuthScreenState(
            emailInputValue = "",
            passwordInputValue = "",
            token = null
        )
    )
    val screenState: State<AuthScreenState> = _screenState
    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    init {

    }

    fun getToken(userName: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
            try {
                val apiResult = getTokenUseCase.execute(userName, password)
                Log.d("MALEO93939319931013", "Testing 1")
                apiResult.data?.let { apiTokenResponse ->
                    screenState.value.token = Token(id = 1, token = apiTokenResponse.token, refreshToken = apiTokenResponse.refreshToken)
                    tokenManager.saveToken(apiTokenResponse.token)
                    tokenManager.saveRefreshToken(apiTokenResponse.refreshToken)
                    getUser(
                        userName = userName,
                        token = screenState.value.token!!.token
                    )
                }
                _screenState.value = _screenState.value.copy(
                    isNetworkConnected = true,
                    isLoad = true,
                    isNetworkError = false,
                    initCallToken = screenState.value.initCallToken++,
                    user = null,
                    currentPage = 1,
                )
            } catch (e: Exception) {
                _screenState.value = _screenState.value.copy(
                    isNetworkError = true,
                    isNetworkConnected = true,
                    isLoad = false
                )
            }
    }

    fun getUser(userName: String, token: String) = viewModelScope.launch(Dispatchers.IO) {
            try {
                val apiResult = getUserUseCase.execute(userName, "Bearer $token")

                apiResult.data?.let { apiUserResponse ->
                    screenState.value.user = apiUserResponse[0]
                    val userRoom = UserRoom(
                        screenState.value.user!!.id,
                        screenState.value.user!!.firstName,
                        screenState.value.user!!.lastName,
                        screenState.value.user!!.roles[0],
                        screenState.value.user!!.phone,
                        screenState.value.user!!.nationality,
                        screenState.value.user!!.sex,
                        screenState.value.user!!.state,
                        screenState.value.user!!.email,
                        screenState.value.user!!.username,
                        screenState.value.user!!.pushNotifications?.get(0)?.keyPush,
                        (if (screenState.value.user!!.images.isNotEmpty()) screenState.value.user!!.images[0].imageName else "")
                    )
                    viewModelScope.launch {
                        saveUserUseCase.execute(
                            user = userRoom
                        )
                    }

                    screenState.value.userRoom = userRoom
                    screenState.value.user!!.id?.let { tokenManager.saveUserId(it) }
                }
                _screenState.value = _screenState.value.copy(
                    isNetworkConnected = true,
                    isLoad = false,
                    isNetworkError = false,
                    initCallToken = screenState.value.initCallUser++
                )
            } catch (e: Exception) {
                _screenState.value = _screenState.value.copy(
                    isNetworkError = true,
                    isNetworkConnected = true,
                    isLoad = false
                )
            }
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.IsInitField -> {
                _screenState.value = _screenState.value.copy(
                    emailInputValue = event.email,
                    passwordInputValue = event.password
                )
            }
            is AuthEvent.EmailValueEntered -> {
                _screenState.value = _screenState.value.copy(
                    emailInputValue = event.value,
                    userRoom = null,
                    user = null,
                    token = null,
                    currentPage = 1,
                    isLoad = false
                )
            }
            is AuthEvent.PasswordValueEntered -> {
                _screenState.value = _screenState.value.copy(
                    passwordInputValue = event.value,
                    userRoom = null,
                    user = null,
                    token = null,
                    currentPage = 1,
                    isLoad = false
                )
            }
            is AuthEvent.GetToken -> {
                _screenState.value = _screenState.value.copy(
                    emailInputValue = event.userName,
                    passwordInputValue = event.password,
                    isLoad = false,
                    currentPage = 1,
                    token = null
                )

                if (isNetworkAvailable(event.app)) {
                    getToken(
                        userName = event.userName,
                        password = event.password
                    )
                } else {
                    _screenState.value = _screenState.value.copy(
                        isNetworkConnected = false,
                        isNetworkError = false,
                        isLoad = false
                    )
                }

            }
            is AuthEvent.GetUser -> {
                _screenState.value = _screenState.value.copy(
                    emailInputValue = event.userName,
                    isLoad = true,
                    currentPage = 1,
                    user = null
                )
                getUser(
                    userName = event.userName,
                    token = event.token
                )
            }
            is AuthEvent.GetSavedUser -> {
                    viewModelScope.launch {
                        getSavedUserUseCase.execute(event.userId).collect {
                            _screenState.value = _screenState.value.copy(
                                userRoom = it
                            )

                            Log.d("MALEO93939319239394949123", "${it.toString()}")
                        }
                    }
            }
            is AuthEvent.InitUserState -> {
                _screenState.value = _screenState.value.copy(
                    isNetworkConnected = true,
                    isNetworkError = false,
                    isLoad = false,
                    currentPage = 1,
                    initCallToken = 0,
                    initCallUser = 0,
                    user = null,
                    userRoom = null,
                    token = null,
                    emailInputValue = "",
                    passwordInputValue = ""
                )
            }
            is AuthEvent.IsNetworkConnected -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = event.errorMessage
                        )
                    )
                }
            }
            is AuthEvent.ConnectionAction -> {
                _screenState.value = _screenState.value.copy(
                    userRoom = null,
                    user = null,
                    token = null,
                    currentPage = 1,
                    isLoad = true
                )
            }
            is AuthEvent.IsNetworkError -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = event.errorMessage
                        )
                    )
                }
            }

            is AuthEvent.IsEmptyField -> {
                if (screenState.value.passwordInputValue.isEmpty()
                    || screenState.value.emailInputValue.isEmpty()
                ) {
                    viewModelScope.launch {
                        _uiEventFlow.emit(
                            UIEvent.ShowMessage(
                                message = event.errorMessage
                            )
                        )
                    }
                }
            }

            else -> {

            }
        }
    }


}