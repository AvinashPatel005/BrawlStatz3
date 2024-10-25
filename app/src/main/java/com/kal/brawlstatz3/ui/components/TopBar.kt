package com.kal.brawlstatz3.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(title = {
        Text(text = "BrawlStatZ", fontWeight = FontWeight.Bold)
    },
        actions = {
            IconButton(onClick = {
            }) {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
            }
        }
    )
}