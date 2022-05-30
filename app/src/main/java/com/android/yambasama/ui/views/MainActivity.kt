package com.android.yambasama.ui.views

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.yambasama.ui.theme.YambaSamaTheme
import com.android.yambasama.ui.views.HomeApp
import com.android.yambasama.ui.views.LaunchView
import com.android.yambasama.ui.views.Login
import com.android.yambasama.ui.views.model.Route
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YambaSamaTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    MainView(navController, this)

                    CoroutineScope(Dispatchers.Main).launch {
                        delay(100)
                        navController.navigate("login_view")
                    }
                }
            }
        }
    }
}

@Composable
@ExperimentalMaterial3Api
fun MainView(navController: NavHostController, context: Any) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    //This LiveData help us to change our bottom navigation view
    NavHost(navController = navController, startDestination = "launch_view") {
        composable(route = Route.launchView) {
            LaunchView()
        }

        composable(route = Route.loginView) {
            Login(navController,/*userViewModel,*/ context)
        }

        composable(route = Route.homeView) {
            HomeApp(navController, scope, drawerState, context/*,  userViewModel*/)
        }

        composable(route = Route.announcement) {
            HomeApp(navController, scope, drawerState, context/*,  userViewModel*/)
        }
    }
}