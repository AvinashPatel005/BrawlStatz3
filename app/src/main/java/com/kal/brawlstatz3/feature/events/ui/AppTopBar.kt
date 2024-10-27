package com.kal.brawlstatz3.feature.events.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.kal.brawlstatz3.feature.brawlers.ui.BrawlersTopBar
import com.kal.brawlstatz3.feature.brawlers.viewmodel.BrawlersViewModel
import com.kal.brawlstatz3.feature.events.viewmodel.EventsViewModel
import com.kal.brawlstatz3.feature.profile.viewmodel.ProfileViewModel
import com.kal.brawlstatz3.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    brawlersViewModel: BrawlersViewModel,
    eventsViewModel: EventsViewModel,
    profileViewModel: ProfileViewModel,
    currentRoute: String
) {
    TopAppBar(
        title = {
            Text("BrawlStatZ")
        },
        actions = {
            if (currentRoute == Screen.Brawlers::class.qualifiedName) {
                BrawlersTopBar(
                    isSearchActive = brawlersViewModel.isSearchActive.value,
                    searchQuery = brawlersViewModel.searchQuery.value,
                    onEvent = {
                        brawlersViewModel.onEvent(it)
                    })
            }
            if (currentRoute == Screen.Events::class.qualifiedName) {
                Text(text = "Events")
            }
            if (currentRoute == Screen.Meta::class.qualifiedName) {
                Text(text = "Meta")
            }
            if (currentRoute == Screen.Profile::class.qualifiedName) {
                Text(text = "Profile")
            }
        }
    )
}