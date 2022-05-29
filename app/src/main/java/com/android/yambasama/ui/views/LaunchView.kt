package com.android.yambasama.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.yambasama.R

@Composable
fun LaunchView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Image(
                painter = painterResource(id = R.drawable.logo2) ,
                contentDescription = "The Application Launcher"
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(60.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "©Yamba 2022 version 1.0", style = MaterialTheme.typography.titleSmall )
    }
}