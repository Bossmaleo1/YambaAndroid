package com.android.yambasama.ui.views.bottomnavigationviews.searchView

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import com.android.yambasama.data.model.dataRemote.Address
import java.util.*


@ExperimentalMaterial3Api
@Composable
fun SearchTownItem(address: Address) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(corner = CornerSize(0.dp)),
        onClick = {
            Log.d("MALEOMALEO9393","${address.id} -------- ${address.townName}")
            // Je dois faire passer les messages de l'addresse avec
            // des séparateurs de - ou , ou ; ou |
            // Puis je lirai ça avec un split pour reconstituer une Address
            // Plutôt de refaire un truc avec du SQLite
        }
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
                    text = "${address.townName} (${address.airportName} (${address.airportCode}) / ${address.code}",
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