package com.kal.brawlstatz3.data.model

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
)
