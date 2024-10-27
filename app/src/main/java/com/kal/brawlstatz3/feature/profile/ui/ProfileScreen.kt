package com.kal.brawlstatz3.feature.profile.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kal.brawlstatz3.feature.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel, modifier: Modifier = Modifier) {
    LazyColumn {
        item {
            Text(text = profileViewModel.profile.value.toString())
        }
    }
}