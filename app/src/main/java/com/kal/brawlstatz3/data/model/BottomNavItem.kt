package com.kal.brawlstatz3.data.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.kal.brawlstatz3.util.Screen

data class BottomNavItem(
    val name: String,
    val route: Screen,
    val icon: ImageVector,
    val selectedIcon: ImageVector
)
