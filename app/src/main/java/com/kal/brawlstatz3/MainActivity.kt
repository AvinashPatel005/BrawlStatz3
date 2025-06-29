package com.kal.brawlstatz3

import android.app.DownloadManager
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kal.brawlstatz3.feature.brawlers.ui.BrawlersScreen
import com.kal.brawlstatz3.feature.brawlers.viewmodel.BrawlersViewModel
import com.kal.brawlstatz3.feature.events.ui.EventsScreen
import com.kal.brawlstatz3.feature.events.viewmodel.EventsViewModel
import com.kal.brawlstatz3.feature.meta.ui.MetaScreen
import com.kal.brawlstatz3.feature.profile.ui.ProfileScreen
import com.kal.brawlstatz3.feature.profile.viewmodel.ProfileViewModel
import com.kal.brawlstatz3.ui.components.AppTopBar
import com.kal.brawlstatz3.ui.components.BottomBar
import com.kal.brawlstatz3.ui.theme.BrawlStatZTheme
import com.kal.brawlstatz3.util.Screen
import com.kal.brawlstatz3.util.UpdateDownloader
import com.kal.brawlstatz3.util.broadcast.UpdateBroadcastReceiver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BrawlStatZTheme {
                val brawlerViewModel = hiltViewModel<BrawlersViewModel>()
                val profileViewModel = hiltViewModel<ProfileViewModel>()
//                val eventsViewModel = hiltViewModel<EventsViewModel>()
                val navController = rememberNavController()

                if (ContextCompat.checkSelfPermission(
                        LocalContext.current,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 202)
                    }
                }
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route?: Screen.Brawlers::class.qualifiedName.orEmpty()

                Scaffold(
                    topBar = {
                        AppTopBar(
                            brawlersViewModel = brawlerViewModel,
                            profileViewModel = profileViewModel,
                            currentRoute = currentRoute
                        )
                    },
                    bottomBar = {
                        BottomBar(
                            currentRoute = currentRoute,
                            onNavigate = { route ->
                                if (route::class.qualifiedName != currentRoute) {
                                    navController.navigate(route) {
                                        launchSingleTop = true
                                        popUpTo(Screen.Brawlers::class.qualifiedName.orEmpty())
                                    }
                                }
                            })
                    },
                    floatingActionButton = {
                        if(brawlerViewModel.isUpdateDownloading.longValue!=0L){
                            Box(contentAlignment = Alignment.Center){
                                CircularProgressIndicator(modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(100)
                                    )
                                    .shadow(10.dp, RoundedCornerShape(100))
                                    .background(MaterialTheme.colorScheme.inversePrimary)
                                    .clickable {
                                        brawlerViewModel.isBottomSheetVisible.value=true
                                    })
                                Image(
                                    painter = painterResource(id = R.drawable.cloud),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                            }
                        }
                        else if (brawlerViewModel.isUpdateAvailable.value){
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.clip(
                                    RoundedCornerShape(100)
                                    )
                                .shadow(10.dp, RoundedCornerShape(100))
                                .clickable {
                                    brawlerViewModel.isBottomSheetVisible.value=true
                                }
                                .background(MaterialTheme.colorScheme.inversePrimary).padding(10.dp)
                                ){
                                Image(
                                    painter = painterResource(id = R.drawable.cloud),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                            }
                        }
                    },
                    modifier = Modifier
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
                            ProfileScreen(profileViewModel = profileViewModel,brawlerList = brawlerViewModel.brawlerlist)
                        }
                    }

                    UpdateBottomSheet(brawlerViewModel, LocalContext.current)
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateBottomSheet(brawlerViewModel: BrawlersViewModel,context: Context) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    if (brawlerViewModel.isBottomSheetVisible.value) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    sheetState.hide()
                }
                brawlerViewModel.isBottomSheetVisible.value = false
            },
            sheetState = sheetState
        ) {
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row {
                        Text("Update Available! ", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text("v"+brawlerViewModel.appState.value.version, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Text("Changes")
                    Column{
                        brawlerViewModel.appState.value.changelog.forEachIndexed { index, s ->
                            Text("${index+1}. $s")
                        }
                    }
                }
                Spacer(Modifier.height(15.dp))
                Button(
                    onClick = {
                        if(brawlerViewModel.isUpdateDownloading.longValue==0L){
                            brawlerViewModel.isBottomSheetVisible.value = false
                            context.registerReceiver(
                                UpdateBroadcastReceiver(),
                                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                                Context.RECEIVER_EXPORTED
                            )

                            brawlerViewModel.isUpdateDownloading.longValue = UpdateDownloader().download(brawlerViewModel.appState.value.link, context)
                        }
                        else{
                            UpdateDownloader().cancelDownload(brawlerViewModel.isUpdateDownloading.longValue,context = context)
                            brawlerViewModel.isUpdateDownloading.longValue = 0L
                        }
                    }
                ) {
                    Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                        if(brawlerViewModel.isUpdateDownloading.longValue!=0L){
                            Icon(imageVector = Icons.Default.Close, contentDescription = null)
                        }
                        else{
                            Image(painter = painterResource(R.drawable.cloud), contentDescription = null)
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if(brawlerViewModel.isUpdateDownloading.longValue!=0L) "Cancel" else "Install")
                    }
                }
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}






