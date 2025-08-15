package com.kal.brawlstatz3.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kal.brawlstatz3.util.BottomNavItem
import com.kal.brawlstatz3.util.Screen

@Composable
fun BottomBar(currentRoute: String, onNavigate: (Screen) -> Unit, modifier: Modifier = Modifier) {
    val bottomNavigationItems = listOf(
        BottomNavItem(
            name = "Brawlers",
            route = Screen.Brawlers,
            icon = Icons.Filled.Face,
            selectedIcon = Icons.Filled.Face
        ),
//        BottomNavItem(
//            name = "Events",
//            route = Screen.Events,
//            icon = Icons.Outlined.Notifications,
//            selectedIcon = Icons.Filled.Notifications
//        ),
        BottomNavItem(
            name = "Meta",
            route = Screen.Meta,
            icon = Icons.Filled.Bolt,
            selectedIcon = Icons.Filled.Bolt
        ),
        BottomNavItem(
            name = "Profile",
            route = Screen.Profile,
            icon = Icons.Filled.AccountCircle,
            selectedIcon = Icons.Filled.AccountCircle
        )

    )
    Row (modifier= Modifier.background(MaterialTheme.colorScheme.surfaceContainer).padding(bottom = 8.dp), verticalAlignment = Alignment.Bottom) {
        bottomNavigationItems.forEach { item ->
            val selected = item.route::class.qualifiedName == currentRoute
            NavigationBarItem(
                selected = selected,
                onClick = {
                    onNavigate(item.route)
                },
                icon = {
                    if (selected) {
                        Icon(imageVector = item.selectedIcon, contentDescription = null)
                    } else {
                        Icon(imageVector = item.icon, contentDescription = null)
                    }
                },
                label = {
                    Text(text = item.name)
                }
            )
        }
    }
}