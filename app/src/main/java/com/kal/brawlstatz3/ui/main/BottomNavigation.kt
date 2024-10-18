package com.kal.brawlstatz3.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kal.brawlstatz3.Routes
import com.kal.brawlstatz3.data.model.BottomNavItem

@Composable
fun BottomNavigation(currentRoute: String, onNavigate:(Routes)->Unit, modifier: Modifier = Modifier) {
    val bottomNavigationItems = listOf(
        BottomNavItem(
            name = "Brawlers",
            route = Routes.Brawlers,
            icon = Icons.Outlined.Face,
            selectedIcon = Icons.Filled.Face
        ),
        BottomNavItem(
            name = "Events",
            route = Routes.Events,
            icon = Icons.Outlined.Notifications,
            selectedIcon = Icons.Filled.Notifications
        ),
        BottomNavItem(
            name = "Meta",
            route = Routes.Meta,
            icon = Icons.Outlined.Star,
            selectedIcon = Icons.Filled.Star
        ),
        BottomNavItem(
            name = "Profile",
            route = Routes.Profile,
            icon = Icons.Outlined.AccountCircle,
            selectedIcon = Icons.Filled.AccountCircle
        )

    )

    BottomAppBar(
        actions = {
                  bottomNavigationItems.forEachIndexed { index, item ->
                      val selected = item.route::class.qualifiedName == currentRoute
                      NavigationBarItem(
                          selected = selected,
                          onClick = {
                              onNavigate(item.route)
                          },
                          icon = {
                              if(selected){
                                  Icon(imageVector = item.selectedIcon, contentDescription = null)
                              }
                              else{
                                  Icon(imageVector = item.icon, contentDescription = null)
                              }
                          },
                          label = {
                              Text(text = item.name)
                          }
                      )
                  }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {

            }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null)
            }
        })
}