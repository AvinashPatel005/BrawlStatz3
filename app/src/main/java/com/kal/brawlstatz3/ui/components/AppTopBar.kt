package com.kal.brawlstatz3.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewModelScope
import com.kal.brawlstatz3.feature.brawlers.ui.BrawlersTopBar
import com.kal.brawlstatz3.feature.brawlers.viewmodel.BrawlersViewModel
import com.kal.brawlstatz3.feature.events.viewmodel.EventsViewModel
import com.kal.brawlstatz3.feature.meta.ui.MetaTopBar
import com.kal.brawlstatz3.feature.profile.ui.ProfileTopBar
import com.kal.brawlstatz3.feature.profile.viewmodel.ProfileViewModel
import com.kal.brawlstatz3.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    brawlersViewModel: BrawlersViewModel,
    profileViewModel: ProfileViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
    currentRoute: String,
    onDrawerClick: ()-> Unit,
    onHomePress: ()->Unit
) {
    TopAppBar(
        title = {
            Text(currentRoute.split(".").last(), fontWeight = FontWeight.Bold)

        },
        scrollBehavior=scrollBehavior,
        navigationIcon = {
            if (currentRoute==Screen.Brawlers::class.qualifiedName||currentRoute==Screen.Meta::class.qualifiedName||currentRoute==Screen.Events::class.qualifiedName||currentRoute==Screen.Profile::class.qualifiedName){
                IconButton(onClick = onDrawerClick) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            }
            else{
                IconButton(onClick = onHomePress) {
                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Menu")
                }
            }
        },
        actions = {
            if (currentRoute == Screen.Brawlers::class.qualifiedName) {
                BrawlersTopBar(
                    isSearchActive = brawlersViewModel.isSearchActive.value,
                    searchQuery = brawlersViewModel.searchQuery.value,
                    onEvent =  brawlersViewModel::onEvent)
            }
            if (currentRoute == Screen.Events::class.qualifiedName) {
            }
            if (currentRoute == Screen.Meta::class.qualifiedName) {
                MetaTopBar(version = brawlersViewModel.appState.value.meta)
            }
            if (currentRoute == Screen.Profile::class.qualifiedName) {
                ProfileTopBar(onSettingClick =  profileViewModel::onEvent)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(scrolledContainerColor = MaterialTheme.colorScheme.surface)
    )
}