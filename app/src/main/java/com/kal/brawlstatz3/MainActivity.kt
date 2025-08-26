package com.kal.brawlstatz3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.initialize
import com.google.firebase.messaging.FirebaseMessaging
import com.kal.brawlstatz3.feature.brawlers.ui.BrawlersScreen
import com.kal.brawlstatz3.feature.brawlers.viewmodel.BrawlersViewModel
import com.kal.brawlstatz3.feature.meta.ui.MetaScreen
import com.kal.brawlstatz3.feature.profile.ui.ProfileScreen
import com.kal.brawlstatz3.feature.profile.viewmodel.ProfileViewModel
import com.kal.brawlstatz3.ui.components.AppTopBar
import com.kal.brawlstatz3.ui.components.BottomBar
import com.kal.brawlstatz3.ui.components.NotificationPermissionSheet
import com.kal.brawlstatz3.ui.theme.AppTheme
import com.kal.brawlstatz3.ui.theme.BrawlStatZTheme
import com.kal.brawlstatz3.util.Screen
import com.kal.brawlstatz3.util.ThemePrefs
import dagger.hilt.android.AndroidEntryPoint
import com.kal.brawlstatz3.feature.about.AboutAppScreen
import com.kal.brawlstatz3.feature.settings.SettingScreen
import com.kal.brawlstatz3.feature.updates.ui.UpdateScreen
import com.kal.brawlstatz3.ui.components.shouldShowBottomBar
import com.kal.brawlstatz3.util.appReset
import com.kal.brawlstatz3.util.getActiveAlias
import com.kal.brawlstatz3.util.rightBorder
import com.kal.brawlstatz3.util.switchIcon
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.google.android.play.core.ktx.isImmediateUpdateAllowed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val navEvents = MutableSharedFlow<Screen>(extraBufferCapacity = 1, replay = 1)
    private lateinit var appUpdateManager: AppUpdateManager
    private val updateType = AppUpdateType.IMMEDIATE

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleNotificationIntent(intent)
    }
    private fun handleNotificationIntent(intent: Intent?) {
        val route = intent?.getStringExtra("route")
        val url = intent?.getStringExtra("url")

        when {
            !route.isNullOrBlank() -> {
                when (route) {
                    "news" -> navEvents.tryEmit(Screen.News)
                    "settings" -> navEvents.tryEmit(Screen.Settings)
                    "about" -> navEvents.tryEmit(Screen.About)
                    "events" -> navEvents.tryEmit(Screen.Events)
                    "meta" -> navEvents.tryEmit(Screen.Meta)
                    "profile" -> navEvents.tryEmit(Screen.Profile)
                    else -> navEvents.tryEmit(Screen.News)
                }
            }
            !url.isNullOrEmpty() -> {
                startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
            }
        }
    }
    private fun isFromNotification(intent: Intent?): Boolean {
        return intent?.extras?.containsKey("url") == true ||
                intent?.extras?.containsKey("route") == true
    }
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        checkForUpdate()

        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel("main_channel", "General", importance)
        mChannel.enableVibration(true)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)

        FirebaseMessaging.getInstance().subscribeToTopic("all")

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    Log.d("FCM", "Device token: $token")
                    // You can send this token to your server
                } else {
                    Log.e("FCM", "Fetching FCM token failed", task.exception)
                }
            }

        if(isFromNotification(intent)) handleNotificationIntent(intent)
        enableEdgeToEdge()
        Firebase.initialize(context = this)
        Firebase.appCheck.installAppCheckProviderFactory(
            if (BuildConfig.DEBUG) {
                DebugAppCheckProviderFactory.getInstance()
            } else {
                PlayIntegrityAppCheckProviderFactory.getInstance()
            }
        )

        setContent {
            var currentTheme by remember { mutableStateOf(ThemePrefs(this).getTheme()) }
            val navController = rememberNavController()
            LaunchedEffect(Unit) {
                navEvents.collect { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                }
            }
            BrawlStatZTheme(currentTheme) {
                val brawlerViewModel = hiltViewModel<BrawlersViewModel>()
                val profileViewModel = hiltViewModel<ProfileViewModel>()
//                val eventsViewModel = hiltViewModel<EventsViewModel>()


                val context = LocalContext.current

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route?: Screen.Brawlers::class.qualifiedName.orEmpty()

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

                ModalNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet(
                            drawerShape = RectangleShape,
                            modifier = Modifier.shadow(10.dp).rightBorder(MaterialTheme.colorScheme.primary,8f),
                            drawerTonalElevation = 1.dp
                        ) {


                            Box(
                                modifier = Modifier.fillMaxHeight().requiredWidth(250.dp)
                            ){
                                Column (modifier = Modifier.padding(12.dp)){
                                Row (verticalAlignment = Alignment.CenterVertically){
                                    Image(painter = painterResource(R.drawable.logo_icon), contentDescription = null, modifier = Modifier.width(56.dp))
                                    Text(text = "Brawlstatz", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)

                                }
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text("App", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(start = 10.dp, bottom = 4.dp))
                                    NavigationDrawerItem(
                                        label = { Text("News") },
                                        selected = false,
                                        icon = { Icon(Icons.Filled.Notifications, contentDescription = null) },
                                        onClick = {
                                            scope.launch {
                                                drawerState.close()
                                                delay(100)
                                                val animatable = Animatable(scrollBehavior.state.heightOffset)
                                                animatable.animateTo(
                                                    targetValue = 0f,
                                                    animationSpec = tween(
                                                        durationMillis = 300,
                                                        easing = FastOutSlowInEasing
                                                    )
                                                ) {
                                                    scrollBehavior.state.heightOffset = value
                                                }
                                            }
                                            navController.navigate(Screen.News)
                                        }
                                    )
//                                    NavigationDrawerItem(
//                                        label = { Text("Downloads") },
//                                        selected = false,
//                                        icon = { Icon(Icons.Filled.Download, contentDescription = null) },
//                                        onClick = {  }
//                                    )
                                    NavigationDrawerItem(
                                        label = { Text("Settings") },
                                        selected = false,
                                        icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
                                        onClick = {
                                            scope.launch {
                                                drawerState.close()
                                                delay(100)
                                                val animatable = Animatable(scrollBehavior.state.heightOffset)
                                                animatable.animateTo(
                                                    targetValue = 0f,
                                                    animationSpec = tween(
                                                        durationMillis = 300,
                                                        easing = FastOutSlowInEasing
                                                    )
                                                ) {
                                                    scrollBehavior.state.heightOffset = value
                                                }
                                            }
                                            navController.navigate(Screen.Settings)
                                        }
                                    )
                                    Text("About", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(start = 10.dp, top = 16.dp, bottom = 4.dp))
                                    val context = LocalContext.current
                                    val playStoreLink = "https://play.google.com/store/apps/details?id=$packageName"

                                    val shareMessage = """
                                                        Hey! 👋
                                                        Check out this awesome app, Brawlstatz
                                                        Download it here: $playStoreLink
                                                    """.trimIndent()
                                    NavigationDrawerItem(
                                        label = { Text("Share") },
                                        selected = false,
                                        icon = { Icon(Icons.Filled.Share, contentDescription = null) },
                                        onClick = {
                                            val sendIntent = Intent().apply {
                                                action = Intent.ACTION_SEND
                                                putExtra(Intent.EXTRA_TEXT, shareMessage)
                                                type = "text/plain"
                                            }

                                            val shareIntent = Intent.createChooser(sendIntent, "Share")
                                            context.startActivity(shareIntent)
                                        }
                                    )
                                    NavigationDrawerItem(
                                        label = { Text("About us") },
                                        selected = false,
                                        icon = { Icon(Icons.Filled.Info, contentDescription = null) },
                                        onClick = {
                                            scope.launch {
                                                drawerState.close()
                                                delay(100)
                                                val animatable = Animatable(scrollBehavior.state.heightOffset)
                                                animatable.animateTo(
                                                    targetValue = 0f,
                                                    animationSpec = tween(
                                                        durationMillis = 300,
                                                        easing = FastOutSlowInEasing
                                                    )
                                                ) {
                                                    scrollBehavior.state.heightOffset = value
                                                }
                                            }
                                            navController.navigate(Screen.About){
                                                launchSingleTop = true
                                            }

                                        }
                                    )
                                }
                                Text("Copyright © ATeamDiversity", fontSize = 10.sp, modifier = Modifier.align(
                                    Alignment.BottomCenter))
                            }
                        }
                    },
                    drawerState = drawerState
                ) {


                    Scaffold(
                        topBar = {
                            AppTopBar(
                                brawlersViewModel = brawlerViewModel,
                                profileViewModel = profileViewModel,
                                currentRoute = currentRoute,
                                scrollBehavior=scrollBehavior,
                                onDrawerClick = {
                                    scope.launch {
                                        if (drawerState.isClosed) {
                                            drawerState.open()
                                        } else {
                                            drawerState.close()
                                        }
                                    }
                                },
                                onHomePress = {
                                    navController.navigate(Screen.Brawlers){
                                        launchSingleTop = true
                                        popUpTo(Screen.Brawlers::class.qualifiedName.orEmpty())
                                    }
                                }
                            )
                        },
                        bottomBar = {
                            if(shouldShowBottomBar(currentRoute)){
                                BottomBar(
                                    currentRoute = currentRoute,
                                    onNavigate = { route ->
                                        if (route::class.qualifiedName != currentRoute) {
                                            navController.navigate(route) {
                                                launchSingleTop = true
                                                popUpTo(Screen.Brawlers::class.qualifiedName.orEmpty())
                                            }
                                            scope.launch {
                                                delay(500)
                                                val animatable = Animatable(scrollBehavior.state.heightOffset)
                                                animatable.animateTo(
                                                    targetValue = 0f,
                                                    animationSpec = tween(
                                                        durationMillis = 300,
                                                        easing = FastOutSlowInEasing
                                                    )
                                                ) {
                                                    scrollBehavior.state.heightOffset = value
                                                }
                                            }
                                        }
                                    })
                            }
                        },
                        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
                            .fillMaxSize()

                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Brawlers,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable<Screen.Brawlers> {
                                BrawlersScreen(brawlersViewModel = brawlerViewModel)
                            }
//                        composable<Screen.Events> {
//                            EventsScreen(eventsViewModel = eventsViewModel)
//                        }
                            composable<Screen.Meta> {
                                MetaScreen(brawlersViewModel = brawlerViewModel)
                            }
                            composable<Screen.Profile> {
                                ProfileScreen(
                                    profileViewModel = profileViewModel,
                                    brawlerList = brawlerViewModel.brawlerlist
                                )
                            }
                            composable<Screen.About> {
                                AboutAppScreen()
                            }
                            composable<Screen.Settings> {

                                SettingScreen(currentTheme=currentTheme, onThemeChange = { i->
                                    ThemePrefs(context).saveTheme(AppTheme.entries[i])
                                    currentTheme = AppTheme.entries[i]
                                }, currentIcon = getActiveAlias(context) ,onIconChange = {
                                    switchIcon(it,context)
                                }, onClearData = {
                                    appReset(context)
                                })
                            }
                            composable<Screen.News> {
                                UpdateScreen()
                            }
                        }
                        if (!brawlerViewModel.isLoading.value) {
                            NotificationPermissionSheet()
                        }

                    }
                }

            }
        }
    }

    private fun checkForUpdate(){
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info->
            val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val isUpdateAllowed = when(updateType){
                AppUpdateType.FLEXIBLE -> info.isFlexibleUpdateAllowed
                AppUpdateType.IMMEDIATE -> info.isImmediateUpdateAllowed
                else -> false
            }

            if(isUpdateAvailable && isUpdateAllowed){
                appUpdateManager.startUpdateFlowForResult(
                    info,
                    updateType,
                    this,
                    11012
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==11012){
            if(resultCode!=RESULT_OK){
                println("Something Went Wrong updating!")
            }
        }
    }
}
