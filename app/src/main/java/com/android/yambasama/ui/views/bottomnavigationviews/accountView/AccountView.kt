package com.android.yambasama.ui.views.bottomnavigationviews.accountView

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.yambasama.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun AccountView(
    navController: NavHostController
) {
    Scaffold(
        topBar = {

            TopAppBar(title = { Text(text = "GFG | Collapsing Toolbar", color = Color.White) })
        },
        content = {
            // Creating a Column Layout
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

                val items = (1..100).map { "Item $it" }
                val lazyListState = rememberLazyListState()
                var scrolledY = 0f
                var prevousOffset = 0
                LazyColumn(
                    Modifier.fillMaxSize(),
                    lazyListState) {
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.ic_profile_colorier),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .graphicsLayer {
                                    scrolledY += lazyListState.firstVisibleItemScrollOffset - prevousOffset
                                    translationY = scrolledY * 0.5f
                                    prevousOffset = lazyListState.firstVisibleItemScrollOffset
                                }
                                .height(400.dp)
                                .fillMaxWidth()
                        )
                    }

                    items(items) {
                        Text(
                            text = it,
                            Modifier
                                .background(Color.White)
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    )
}
