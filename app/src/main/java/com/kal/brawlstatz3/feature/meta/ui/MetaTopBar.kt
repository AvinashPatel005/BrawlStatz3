package com.kal.brawlstatz3.feature.meta.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MetaTopBar(version:String,modifier: Modifier = Modifier) {
    Text(text=version, fontSize = 12.sp, modifier = Modifier.padding(end = 10.dp))
}