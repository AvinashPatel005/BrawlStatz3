package com.kal.brawlstatz3.data.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.kal.brawlstatz3.Routes

data class BottomNavItem(
    val name: String,
    val route: Routes,
    val icon: ImageVector,
    val selectedIcon: ImageVector
)
