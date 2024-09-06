package com.kal.brawlstatz3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.storage.FirebaseStorage
import com.kal.brawlstatz3.ui.main.TopBar
import com.kal.brawlstatz3.ui.brawlers.BrawlersScreen
import com.kal.brawlstatz3.ui.main.BottomNavigation
import com.kal.brawlstatz3.ui.main.ViewModel
import com.kal.brawlstatz3.ui.theme.BrawlStatZTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BrawlStatZTheme {
                val viewModel = hiltViewModel<ViewModel>()
                val navController = rememberNavController()

                //Get Current Route
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route ?: Routes.Brawlers::class.qualifiedName.orEmpty()

                Scaffold(
                    topBar = {
                        TopBar()
                    },
                    bottomBar = {
                        BottomNavigation(
                            currentRoute = currentRoute,
                            onNavigate = { route->
                            navController.navigate(route)
                        })
                    },
                    modifier = Modifier
                        .fillMaxSize()

                ) { innerPadding ->
                    NavHost(navController = navController, startDestination = Routes.Brawlers, modifier = Modifier.padding(innerPadding)) {
                        composable<Routes.Brawlers>{
                            Column() {
                                if(viewModel.isLoading.value){
                                    Text(text = "Loading...")
                                }
                                else {
                                    BrawlersScreen()
                                }
//                                var isExp by remember{
//                                    mutableStateOf(false)
//                                }
//                                Card(onClick = {
//                                    isExp = !isExp
//                                }, modifier =Modifier) {
//                                    Row(modifier = Modifier.height(100.dp)) {
//                                        Text(text = "FPS")
//                                    }
//                                    if(isExp){
//                                        Row(modifier = Modifier.height(100.dp)) {
//                                            Text(text = "SPS")
//                                        }
//                                    }
//                                }
                            }
                        }
                        composable<Routes.Events> {
                            Text(text = "Events")
                        }
                        composable<Routes.Meta> {
                            Text(text = "Meta")
                        }
                        composable<Routes.Profile> {
                            Text(text = "profile")
                        }
                    }
                }
            }
        }
    }
}

sealed class Routes{
    @Serializable
    data object Brawlers:Routes()

    @Serializable
    data object Meta:Routes()

    @Serializable
    data object Profile:Routes()

    @Serializable
    data object Events:Routes()
}