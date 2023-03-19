package com.android.yambasama.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.yambasama.presentation.viewModel.address.AddressViewModel
import com.android.yambasama.presentation.viewModel.address.AddressViewModelFactory
import com.android.yambasama.presentation.viewModel.announcement.AnnouncementViewModel
import com.android.yambasama.presentation.viewModel.announcement.AnnouncementViewModelFactory
import com.android.yambasama.presentation.viewModel.drop.DropViewModel
import com.android.yambasama.presentation.viewModel.drop.DropViewModelFactory
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModel
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModelFactory
import com.android.yambasama.presentation.viewModel.user.UserViewModel
import com.android.yambasama.presentation.viewModel.user.UserViewModelFactory
import com.android.yambasama.ui.UIEvent.Event.AddressEvent
import com.android.yambasama.ui.theme.YambaSamaTheme
import com.android.yambasama.ui.views.HomeApp
import com.android.yambasama.ui.views.LaunchView
import com.android.yambasama.ui.views.bottomnavigationviews.accountView.AccountDetailView
import com.android.yambasama.ui.views.bottomnavigationviews.accountView.AccountEditView
import com.android.yambasama.ui.views.bottomnavigationviews.accountView.AccountView
import com.android.yambasama.ui.views.bottomnavigationviews.annoucement.announcementDetails.AnnouncementDetails
import com.android.yambasama.ui.views.bottomnavigationviews.annoucement.announcementDetails.announcementlist.AnnouncementView
import com.android.yambasama.ui.views.bottomnavigationviews.notifications.notificationlistView.NotificationListView
import com.android.yambasama.ui.views.bottomnavigationviews.paymentView.PaymentView
import com.android.yambasama.ui.views.login
import com.android.yambasama.ui.views.bottomnavigationviews.searchview.SearchAddress
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
    @Inject
    lateinit var addressFactory: AddressViewModelFactory
    @Inject
    lateinit var searchFormFactory: SearchFormViewModelFactory
    @Inject
    lateinit var announcementFactory: AnnouncementViewModelFactory

    private lateinit var userViewModel: UserViewModel
    private lateinit var addressViewModel: AddressViewModel
    private lateinit var dropViewModel: DropViewModel
    private lateinit var searchFormViewModel: SearchFormViewModel
    private lateinit var announcementViewModel: AnnouncementViewModel
    var token: String? = null

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YambaSamaTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    MainView(navController)
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
        addressViewModel = ViewModelProvider(this,addressFactory)[AddressViewModel::class.java]
        searchFormViewModel = ViewModelProvider(this,searchFormFactory)[SearchFormViewModel::class.java]
        announcementViewModel = ViewModelProvider(this,announcementFactory)[AnnouncementViewModel::class.java]
    }

    @Composable
    @ExperimentalMaterial3Api
    fun MainView(navController: NavHostController) {

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
                login(navController,userViewModel)
                BackHandler {
                    activity?.finish()
                }
            }

            composable(
                route = Route.homeView
                /*arguments = listOf(
                    navArgument("userAddress") {
                            type = NavType.StringType
                    }
                )*/
            ) {
                if (userViewModel.screenState.value.userRoom.isEmpty()
                    && userViewModel.screenState.value.tokenRoom.isEmpty()) {
                    addressViewModel.onEvent(AddressEvent.InitAddressState)
                }
                HomeApp(
                    navController = navController,
                    dropViewModel = dropViewModel,
                    userViewModel = userViewModel,
                    searchFormViewModel = searchFormViewModel,
                    announcementViewModel = announcementViewModel
                    //addressData =  it.arguments?.getString("inputName").toString()
                )
            }

            composable(
                route = Route.searchLocalizeView
                /*arguments = listOf(
                    navArgument("pointTravel") {
                        type = NavType.StringType
                    }
                )*/
            ) {
                SearchAddress(
                    navController = navController,
                    addressViewModel = addressViewModel,
                    userViewModel = userViewModel,
                    searchFormViewModel = searchFormViewModel
                    //addressParam = it.arguments?.getString("pointTravel").toString()
                )
            }

            composable(
                route = Route.detailsView
            ) {
                AnnouncementDetails(
                    navController = navController,
                    announcementViewModel = announcementViewModel,
                    searchFormViewModel = searchFormViewModel
                )
            }

            composable(
                route = Route.announcementList
            ) {
                AnnouncementView(
                    navController = navController,
                    userViewModel = userViewModel,
                    searchFormViewModel = searchFormViewModel,
                    announcementViewModel = announcementViewModel
                )
            }

            composable(
                route = Route.accountView
            ) {
                AccountView(
                    navController = navController,
                    user = userViewModel.screenState.value.userRoom[0]
                )
            }

            composable(
                route = Route.accountEditView
            ) {
                AccountEditView(
                    navController = navController,
                    user = userViewModel.screenState.value.userRoom[0]
                )
            }

            composable(
                route = Route.accountDetailView
            ) {
                AccountDetailView(
                    navController = navController
                )
            }

            composable(
                route = Route.notificationListView
            ) {
                NotificationListView(
                    navController = navController
                )
            }

            composable(
                route = Route.paymentView
            ) {
                PaymentView(
                    navController = navController
                )
            }
        }
    }
}

