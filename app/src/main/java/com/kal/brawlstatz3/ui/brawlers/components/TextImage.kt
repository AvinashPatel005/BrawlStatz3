package com.kal.brawlstatz3.ui.brawlers.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BoxScope.TextImage(name:String, profile:String,size:Dp,nameVisibility:Boolean, offsetX: Dp, rotation:Float, alignment: Alignment, modifier: Modifier = Modifier) {
    Row(modifier = modifier.align(
        alignment
    ),
        verticalAlignment = Alignment.Bottom) {
        AnimatedVisibility(visible = nameVisibility ) {
            Text(text = name, fontSize = 10.sp , lineHeight = 16.sp, modifier = Modifier.padding(end = 2.dp))
        }
        GlideImage(model = profile, contentDescription = null,modifier = Modifier.size(size).offset(x = -offsetX)
            .rotate(rotation))
    }
}