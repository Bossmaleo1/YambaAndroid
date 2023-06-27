package com.android.yambasama.ui.UIEvent.ScreenState.AnnouncementFormScreenState

import android.location.Address
import com.android.yambasama.data.model.api.NumberOfKgBody

data class AnnouncementFormScreenState(
    var departureAddress: Address? = null,
    var destinationAddress: Address? = null,
    var departureDate:  String? = null,
    var departureTime: String? = null,
    var arrivingTime: String? = null,
    var arrivingDate: String? = null,
    var price: Float,
    var numberOfKg: NumberOfKgBody? = null
)
