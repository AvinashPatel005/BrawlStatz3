package com.kal.brawlstatz3.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.RippleDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Row (modifier= Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceContainer).windowInsetsPadding(
        WindowInsets.navigationBars), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.SpaceEvenly) {
        bottomNavigationItems.forEach { item ->
            val interaction = remember { MutableInteractionSource() }
            val selected = item.route::class.qualifiedName == currentRoute
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f).padding(horizontal = 10.dp, vertical = 8.dp).clip(
                RoundedCornerShape(8.dp)
            ).clickable(indication = null, interactionSource = interaction,onClick = {
                onNavigate(item.route)
                interaction.tryEmit(PressInteraction.Press(Offset.Zero))
                interaction.tryEmit(PressInteraction.Release(PressInteraction.Press(Offset.Zero)))
            })) {
                Icon(imageVector =  item.icon, contentDescription = null, tint = if(selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground, modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(if(selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent).indication(
                    interactionSource = interaction,
                    indication = ripple(bounded = false)
                ).padding(horizontal = 16.dp, vertical = 1.dp))
                Text(text = item.name, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
            }

//            NavigationBarItem(
//                selected = selected,
//                onClick = {
//                    onNavigate(item.route)
//                },
//                icon = {
//                    if (selected) {
//                        Icon(imageVector = item.selectedIcon, contentDescription = null,modifier= Modifier.height(20.dp))
//                    } else {
//                        Icon(imageVector = item.icon, contentDescription = null,modifier= Modifier.height(20.dp))
//                    }
//                },
//                label = {
//                    Text(text = item.name, fontSize = 12.sp)
//                },
//            )
        }
    }
}

fun shouldShowBottomBar(route: String?): Boolean {
    return when (route) {
        Screen.Brawlers::class.qualifiedName,
        Screen.Profile::class.qualifiedName,
        Screen.Meta::class.qualifiedName,
        Screen.Events::class.qualifiedName,-> true
        else -> false
    }
}