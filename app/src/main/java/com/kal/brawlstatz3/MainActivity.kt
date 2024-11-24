package com.kal.brawlstatz3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                val currentRoute = navBackStackEntry?.destination?.route?: Screen.Brawlers::class.qualifiedName.orEmpty()

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet {

                        }
                    },
                    drawerState = drawerState
                ) {
                    Scaffold(
                        topBar = {
                            AppTopBar(
                                brawlersViewModel = brawlerViewModel,
                                eventsViewModel = eventsViewModel,
                                profileViewModel = profileViewModel,
                                currentRoute = currentRoute,
                                onDrawerClicked = {
                                    scope.launch {
                                        if (drawerState.isOpen) drawerState.close() else drawerState.open()
                                    }
                                })
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
                            composable<Screen.Events> {
                                EventsScreen(eventsViewModel = eventsViewModel)
                            }
                            composable<Screen.Meta> {
                                MetaScreen(brawlersViewModel = brawlerViewModel)
                            }
                            composable<Screen.Profile> {
                                ProfileScreen(profileViewModel = profileViewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}







