package com.android.yambasama.ui.views.bottomnavigationviews.searchView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@ExperimentalMaterial3Api
@Composable
fun SearchTownItem() {
    /**
    modifier = Modifier
    .fillMaxWidth()
    .wrapContentHeight()
    .padding(2.5.dp),
    shape = RoundedCornerShape(corner = CornerSize(10.dp))
     * */
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(corner = CornerSize(0.dp)),
        //colors = CardDefaults.cardColors(Color.Transparent),
        onClick = {}
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Aeroport Maya Maya (Brazzaville)",
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }

        Divider(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(0.20.dp),
        )
    }
}