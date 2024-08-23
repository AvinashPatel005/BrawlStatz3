package com.kal.brawlstatz3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.kal.brawlstatz3.data.model.Brawler
import com.kal.brawlstatz3.data.model.TopBar
import com.kal.brawlstatz3.ui.main.BottomNavigation
import com.kal.brawlstatz3.ui.main.UiEvent
import com.kal.brawlstatz3.ui.main.ViewModel
import com.kal.brawlstatz3.ui.theme.BrawlStatZTheme
import com.kal.brawlstatz3.util.FirebaseStorageUtil
import com.kal.brawlstatz3.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BrawlStatZTheme {
                val viewModel = hiltViewModel<ViewModel>()
                LaunchedEffect(key1 = true) {
                    viewModel.uiEvent.collect { event ->
                        when (event) {
                            is UiState.Success -> {
                                viewModel.onUiEvent(UiEvent.OnDataReceived(event.list, event.map))
                            }
                            is UiState.Error -> {
                                viewModel.onUiEvent(UiEvent.OnError(event.message))
                            }
                            is UiState.Loading -> {
                                viewModel.onUiEvent(UiEvent.OnLoading)
                            }
                        }
                    }
                }
                Scaffold(
                    topBar = {
                        TopBar()
                    },
                    bottomBar = {
                        BottomNavigation()
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        if(viewModel.isLoading.value){
                            Text(text = "Loading...")
                        }
                        else{
                            LazyColumn {
                                items(viewModel.list) { brawler ->
                                    Text(text = brawler.name)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
