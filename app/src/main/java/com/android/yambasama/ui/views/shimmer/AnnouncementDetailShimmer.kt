package com.android.yambasama.ui.views.shimmer

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable

@ExperimentalMaterial3Api
@Composable
fun AnnouncementDetailsShimmer(iterator: Int) {
    for(i in 1..iterator) {
        ShimmerGridItemAnnouncementDetails(brush = getBrush())
    }
}