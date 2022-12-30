package com.android.yambasama.ui.views.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.android.yambasama.data.model.dataRemote.Address
import com.android.yambasama.presentation.viewModel.address.AddressViewModel
import com.android.yambasama.ui.views.bottomnavigationviews.searchView.SearchTownItem
import com.android.yambasama.ui.views.shimmer.AddressShimmer

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfiniteListAddressRemote(
    listState: LazyListState,
    listItems: SnapshotStateList<Address>,
    paddingValues: PaddingValues,
    addressViewModel: AddressViewModel,
    isoCode: String,
    code: String,
    airportCode: String,
    airportName: String,
    townName: String,
    token: String,
) {

    LazyColumn(
        contentPadding = paddingValues, //PaddingValues(),
        state = listState
    ) {

        items(listItems) { address ->
            SearchTownItem()
        }

        items(count = 1) {
            AddressShimmer()
        }
    }

    listState.OnBottomReached(buffer = 2) {
        addressViewModel.getAddress(
            isoCode,
            code,
            airportCode,
            airportName,
            townName,
            page = addressViewModel.currentPage.value+1,
            pagination = true,
            token
        )
    }

}

@Composable
fun LazyListState.OnBottomReached(
    buffer: Int = 0,
    loadMore : () -> Unit
){
    // Buffer must be positive.
    // Or our list will never reach the bottom.
    require(buffer >= 0) { "buffer cannot be negative, but was $buffer" }

    // state object which tells us if we should load more
    val shouldLoadMore = remember {
        derivedStateOf {
            // get last visible item
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?:
                // list is empty
                // return false here if loadMore should not be invoked if the list is empty
                return@derivedStateOf true
            // Check if last visible item is the last item in the list
            lastVisibleItem.index == layoutInfo.totalItemsCount - 1 - buffer
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect {
                // if should load more, then invoke loadMore
                if (it) loadMore()
            }
    }
}
