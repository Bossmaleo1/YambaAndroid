package com.android.yambasama.ui.views.viewsMessage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.SignalCellularConnectedNoInternet0Bar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun emptyMessage(
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
                when (iconValue) {
                    0 -> {
                        Icons.Outlined.SignalCellularConnectedNoInternet0Bar
                    }
                    1 -> {
                        Icons.Outlined.Block
                    }
                    2-> {
                        Icons.Outlined.Lightbulb
                    }
                    else -> {
                        Icons.Outlined.Block
                    }
                },
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                tint = Color.Green
            )

            Text(
                text = title,
                color = Color.Green,
                modifier = Modifier.padding(start = 4.dp, top = 10.dp)
            )
        }
    }
}