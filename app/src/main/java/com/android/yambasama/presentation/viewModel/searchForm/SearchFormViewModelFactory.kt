package com.android.yambasama.presentation.viewModel.searchForm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class SearchFormViewModelFactory @Inject constructor(
    private val app: Application,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchFormViewModel(app) as T
    }
}