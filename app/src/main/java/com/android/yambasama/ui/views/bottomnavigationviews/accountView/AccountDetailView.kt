package com.android.yambasama.ui.views.bottomnavigationviews.accountView

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.android.yambasama.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun AccountDetailView(
    navController: NavHostController
) {
    val isDark = isSystemInDarkTheme()
    val scaffoldState = rememberScaffoldState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "",
                            tint = if (!isDark) {
                                colorResource(R.color.black40)
                            } else {
                                Color.White
                            }
                        )
                    }
                },
                actions = {

                },
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        text = stringResource(R.string.profile_details),
                        color = if (!isDark) {
                            colorResource(R.color.black40)
                        } else {
                            Color.White
                        },
                        fontWeight = FontWeight.Normal
                    )
                }
            )
        },
        content = { innerPadding -> })
}