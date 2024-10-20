package com.kal.brawlstatz3.util

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val name: String,
    val route: Screen,
    val icon: ImageVector,
    val selectedIcon: ImageVector
)
