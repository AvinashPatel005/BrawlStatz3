package com.kal.brawlstatz3.feature.events.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kal.brawlstatz3.feature.events.viewmodel.EventsViewModel

@Composable
fun EventsScreen(eventsViewModel: EventsViewModel, modifier: Modifier = Modifier) {
    Text(text = eventsViewModel.currentMaps.toString())
}