package com.android.yambasama.presentation.viewModel.address

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.yambasama.domain.usecase.address.GetAddressUseCase

class AddressViewModelFactory(
    private val app: Application,
    private val getAddressUseCase: GetAddressUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddressViewModel(
            app,
            getAddressUseCase
        ) as T
    }
}