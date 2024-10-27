package com.kal.brawlstatz3.feature.meta.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kal.brawlstatz3.feature.profile.viewmodel.ProfileViewModel

@Composable
fun MetaScreen(profileViewModel: ProfileViewModel, modifier: Modifier = Modifier) {
    LazyColumn {
        item {
            Text(text = profileViewModel.club.value.toString())
        }
    }
}