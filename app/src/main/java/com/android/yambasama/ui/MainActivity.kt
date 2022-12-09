package com.android.yambasama.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.yambasama.presentation.viewModel.drop.DropViewModel
import com.android.yambasama.presentation.viewModel.drop.DropViewModelFactory
import com.android.yambasama.presentation.viewModel.user.UserViewModel
import com.android.yambasama.presentation.viewModel.user.UserViewModelFactory
import com.android.yambasama.ui.theme.YambaSamaTheme
import com.android.yambasama.ui.views.HomeApp
import com.android.yambasama.ui.views.LaunchView
import com.android.yambasama.ui.views.Login
import com.android.yambasama.ui.views.bottomnavigationviews.searchView.SearchLocation
import com.android.yambasama.ui.views.model.Route
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userFactory: UserViewModelFactory
    @Inject
    lateinit var dropFactory: DropViewModelFactory
    private lateinit var userViewModel: UserViewModel
    private lateinit var dropViewModel: DropViewModel
    var token: String? = null

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YambaSamaTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    MainView(navController, this)
                    userViewModel.getSavedToken().observe(this as LifecycleOwner) { token ->
                        this.token = token?.token
                    }

                    CoroutineScope(Dispatchers.Main).launch {
                        delay(200)
                        if (token === null) {
                            navController.navigate(Route.loginView)
                        } else {
                            navController.navigate(Route.homeView)
                        }
                    }
                }
            }
        }
    }

    private fun initViewModel() {
        userViewModel = ViewModelProvider(this, userFactory)[UserViewModel::class.java]
        dropViewModel = ViewModelProvider(this, dropFactory)[DropViewModel::class.java]
    }

    @Composable
    @ExperimentalMaterial3Api
    fun MainView(navController: NavHostController, context: Any) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val activity = (LocalContext.current as? Activity)
        //We call our init view model method
        this.initViewModel()
        //This LiveData help us to change our bottom navigation view
        NavHost(navController = navController, startDestination = "launch_view") {
            composable(route = Route.launchView) {
                LaunchView()
                BackHandler {
                    activity?.finish()
                }
            }

            composable(route = Route.loginView) {
                Login(navController,userViewModel, context)
                BackHandler {
                    activity?.finish()
                }
            }

            composable(route = Route.homeView) {
                HomeApp(navController, scope, drawerState, context,dropViewModel,userViewModel)
            }

            composable(route = Route.searchLocalizeView) {
                SearchLocation(navController)
            }
        }
    }
}

