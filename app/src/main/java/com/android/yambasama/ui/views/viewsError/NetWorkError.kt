package com.android.yambasama.ui.views.viewsError

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.SignalWifiStatusbarConnectedNoInternet4
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.yambasama.R

@Composable
fun networkError(
    title: String,
    iconValue: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                if (iconValue == 0) {
                    Icons.Outlined.SignalWifiStatusbarConnectedNoInternet4
                } else {
                    Icons.Outlined.Block
                },
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                tint = Color.Red
            )

            Text(
                text = title,
                color = Color.Red,
                modifier = Modifier.padding(start = 4.dp, top = 10.dp)
            )
        }
    }
}