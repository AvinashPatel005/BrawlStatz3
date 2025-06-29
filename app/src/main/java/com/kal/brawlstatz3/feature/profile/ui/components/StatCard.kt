package com.kal.brawlstatz3.feature.profile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.kal.brawlstatz3.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StatCard(icon:String,name:String,value: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically){
            GlideImage(model = icon, contentDescription = null, failure = placeholder(
                R.drawable.superdamage),modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = name, fontSize = 14.sp)
        }
        Text(text = value , fontSize = 14.sp)
    }
}