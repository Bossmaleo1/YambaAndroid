package com.android.yambasama.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.RemoteInput
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
import com.android.yambasama.ui.views.bottomnavigationviews.createAnnouncementView.AnnouncementWellDone
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
import com.android.yambasama.ui.views.bottomnavigationviews.notice.AddNoticeView
import com.android.yambasama.ui.views.bottomnavigationviews.notice.NoticeView
import com.android.yambasama.ui.views.bottomnavigationviews.request.TransportePackageRequestView
import com.android.yambasama.ui.views.bottomnavigationviews.tracking.TrackerPackageView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

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

    private val channelID = "com.android.yambasama.ui.views.channel1"
    private var notificationManager: NotificationManager? = null
    private val KEY_REPLY = "key_reply"


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YambaSamaTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    MainView(navController)
                    notificationManager =
                        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                    userViewModel.getSavedToken().observe(this as LifecycleOwner) { token ->
                        this.token = token?.token
                    }

                    receiveInput()

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
        addressViewModel = ViewModelProvider(this, addressFactory)[AddressViewModel::class.java]
        searchFormViewModel =
            ViewModelProvider(this, searchFormFactory)[SearchFormViewModel::class.java]
        announcementViewModel =
            ViewModelProvider(this, announcementFactory)[AnnouncementViewModel::class.java]
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun displayNotification() {
        /*val notificationId = 45
        val tapResult = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK //Intent.FLAG_ACTIVITY_FORWARD_RESULT
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            tapResult,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification = NotificationCompat.Builder(this@MainActivity,channelID)
            .setContentTitle("Demo title")
            .setContentText("This is a demo notification")
            .setSmallIcon(R.drawable.notification_logo)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.logo2))
            .setContentIntent(pendingIntent)
            .build()

        notificationManager?.notify(notificationId,notification)*/


        // action Button 1
        /*val notificationId = 45

        val tapResult = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK //Intent.FLAG_ACTIVITY_FORWARD_RESULT
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            tapResult,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val action: NotificationCompat.Action =
            NotificationCompat.Action.Builder(0, "Details", pendingIntent).build()


        val notification = NotificationCompat.Builder(this@MainActivity,channelID)
            .setContentTitle("Demo title")
            .setContentText("This is a demo notification")
            .setSmallIcon(R.drawable.notification_logo)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.logo2))
            .setContentIntent(pendingIntent)
            .addAction(action)
            .build()*/

        // action Button 2

        //Celui là marche très très bien
        /*val notificationId = 45

        val tapResult = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK //Intent.FLAG_ACTIVITY_FORWARD_RESULT
        }

        tapResult.putExtra("","")

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            tapResult,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )


        val action: NotificationCompat.Action =
            NotificationCompat.Action.Builder(0, "ACCEPTER", pendingIntent).build()

        val action1: NotificationCompat.Action =
            NotificationCompat.Action.Builder(0, "REFUSER", pendingIntent).build()


        val notification = NotificationCompat.Builder(this@MainActivity,channelID)
            .setContentTitle("Sidney MALEO")
            .setContentText("Voulez-vous transporter ce colis ??")
            .setSmallIcon(R.drawable.notification_logo)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLargeIcon(getCircleBitmap(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.photo)))
            .setContentIntent(pendingIntent)
            .addAction(action)
            .addAction(action1)
            .build()*/

        /*val notificationId = 45

        val tapResult = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK //Intent.FLAG_ACTIVITY_FORWARD_RESULT
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            tapResult,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // reply action
        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_REPLY).run {
            setLabel("Insert your name")
            build()
        }

        val replyAction : NotificationCompat.Action = NotificationCompat.Action.Builder(
            0,
            "REPLY",
            pendingIntent
        ).addRemoteInput(remoteInput)
            .build()

        val action: NotificationCompat.Action =
            NotificationCompat.Action.Builder(0, "VALIDER", pendingIntent).build()

        val action1: NotificationCompat.Action =
            NotificationCompat.Action.Builder(0, "ANNULER", pendingIntent).build()


        val notification = NotificationCompat.Builder(this@MainActivity,channelID)
            .setContentTitle("Demo title")
            .setContentText("This is a demo notification")
            .setSmallIcon(R.drawable.notification_logo)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.logo2))
            .addAction(action)
            .addAction(action1)
            .addAction(replyAction)
            .build()*/


        // notificationManager?.notify(notificationId,notification)
    }

    private fun receiveInput() {
        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null) {
            val inputString = remoteInput.getCharSequence(KEY_REPLY).toString()
        }
    }

    private fun createNotificationChannel(
        id: String,
        name: String,
        channelDescription: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        }
    }

    @SuppressLint("PermissionLaunchedDuringComposition")
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    @ExperimentalMaterial3Api
    fun MainView(navController: NavHostController) {
        var visibleCurrentForm = remember { mutableStateOf(true) }
        var visibleNextForm = remember { mutableStateOf(false) }
        var switch = rememberSaveable { mutableStateOf(true) }
        var selectedItem = remember { mutableStateOf(0) }
        val notificationPermissionState = rememberPermissionState(
            android.Manifest.permission.POST_NOTIFICATIONS
        )
        val announcementDetailsListState = rememberLazyListState()
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
                    && userViewModel.screenState.value.tokenRoom.isEmpty()
                ) {
                    addressViewModel.onEvent(AddressEvent.InitAddressState)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    //we launch the notification permission
                    notificationPermissionState.launchPermissionRequest()
                    //if the permission is Granted
                    if (notificationPermissionState.status.isGranted) {
                        createNotificationChannel(channelID, "DemoChannel", "this is maleo-sama !!")
                        displayNotification()
                    }
                } else {
                    createNotificationChannel(channelID, "DemoChannel", "this is maleo-sama !!")
                    //displayNotification()
                }


                HomeApp(
                    navController = navController,
                    dropViewModel = dropViewModel,
                    userViewModel = userViewModel,
                    searchFormViewModel = searchFormViewModel,
                    announcementViewModel = announcementViewModel,
                    visibleCurrentForm = visibleCurrentForm,
                    visibleNextForm = visibleNextForm,
                    switch = switch,
                    selectedItem = selectedItem
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
                    searchFormViewModel = searchFormViewModel,
                    userViewModel = userViewModel,
                    listState = announcementDetailsListState
                )
            }

            composable(
                route = Route.announcementList
            ) {
                AnnouncementView(
                    navController = navController,
                    userViewModel = userViewModel,
                    searchFormViewModel = searchFormViewModel,
                    announcementViewModel = announcementViewModel,
                    listState = rememberLazyListState()
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

            composable(
                route = Route.noticeView
            ) {
                NoticeView(
                    navController = navController
                )
            }

            composable(
                route = Route.trackerPackageView
            ) {
                TrackerPackageView(
                    navController = navController
                )
            }

            composable(
                route = Route.addNoticeView
            ) {
                AddNoticeView(
                    navController = navController
                )
            }

            composable(
                route = Route.transportePackageRequestView
            ) {
                TransportePackageRequestView(
                    navController = navController
                )
            }

            composable(
                route = Route.announcementDone
            ) {
                AnnouncementWellDone(
                    navController = navController,
                    announcementViewModel = announcementViewModel
                )
            }
        }
    }

    fun getCircleBitmap(bitmap: Bitmap): Bitmap {
        val output: Bitmap = Bitmap.createBitmap(
            bitmap.width,
            bitmap.height, Bitmap.Config.ARGB_8888
        )
        val canvas: Canvas = Canvas(output)
        val color = Color.Red
        val paint: Paint = Paint()
        val rect: Rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF: RectF = RectF(rect)

        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        //paint.color = color
        canvas.drawOval(rectF, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)

        bitmap.recycle()

        return output
    }
}

