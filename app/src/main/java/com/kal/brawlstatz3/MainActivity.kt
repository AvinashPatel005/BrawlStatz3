package com.kal.brawlstatz3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kal.brawlstatz3.feature.brawlers.ui.BrawlersScreen
import com.kal.brawlstatz3.feature.brawlers.viewmodel.BrawlersViewModel
import com.kal.brawlstatz3.feature.events.viewmodel.EventsViewModel
import com.kal.brawlstatz3.feature.profile.viewmodel.ProfileViewModel
import com.kal.brawlstatz3.ui.components.BottomBar
import com.kal.brawlstatz3.ui.components.TopBar
import com.kal.brawlstatz3.ui.theme.BrawlStatZTheme
import com.kal.brawlstatz3.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BrawlStatZTheme {
                val brawlerViewModel = hiltViewModel<BrawlersViewModel>()
                val profileViewModel = hiltViewModel<ProfileViewModel>()
                val eventsViewModel = hiltViewModel<EventsViewModel>()
                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Brawlers::class.qualifiedName.orEmpty()

                Scaffold(
                    topBar = {
                        TopBar()
                    },
                    bottomBar = {
                        BottomBar(
                            currentRoute = currentRoute,
                            onNavigate = { route->
                                if(route::class.qualifiedName!=currentRoute){
                                    navController.navigate(route){
                                        launchSingleTop = true
                                        popUpTo(Screen.Brawlers::class.qualifiedName.orEmpty())
                                    }
                                }
                            })
                    },
                    modifier = Modifier
                        .fillMaxSize()

                ) { innerPadding ->
                    NavHost(navController = navController, startDestination = Screen.Brawlers, modifier = Modifier.padding(innerPadding)) {
                        composable<Screen.Brawlers>{
                            Column() {
                                if(brawlerViewModel.isLoading.value){
                                    CircularProgressIndicator()

                                }
                                else {
                                    BrawlersScreen(brawlersViewModel = brawlerViewModel)
                                }
                            }

                        }
                        composable<Screen.Events> {
                            if(eventsViewModel.isLoading.value){
                                CircularProgressIndicator()

                            }
                            else {
                                Events(eventsViewModel = eventsViewModel)
                            }

                        }
                        composable<Screen.Meta> {
                            if(profileViewModel.isLoading.value){
                                CircularProgressIndicator()

                            }
                            else {
                                Meta(profileViewModel = profileViewModel)
                            }

                        }
                        composable<Screen.Profile> {
                            if(profileViewModel.isLoading.value){
                                CircularProgressIndicator()

                            }
                            else {
                                Profile(profileViewModel = profileViewModel)
                            }

                        }
                    }
                }

            }
        }
    }
}

@Composable
fun Events(modifier: Modifier = Modifier,eventsViewModel: EventsViewModel) {
    Text(text = eventsViewModel.currentMaps.toString())
}

@Composable
fun Profile(modifier: Modifier = Modifier,profileViewModel: ProfileViewModel) {
    LazyColumn {
        item{
            Text(text = profileViewModel.profile.value.toString())
        }
    }
}
@Composable
fun Meta(modifier: Modifier = Modifier,profileViewModel: ProfileViewModel) {
    LazyColumn {
        item{
            Text(text = profileViewModel.club.value.toString())
        }
    }
}

