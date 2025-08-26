package com.kal.brawlstatz3.feature.brawlers.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.kal.brawlstatz3.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PinBox(pinURL: String, backgroundColor: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .padding(horizontal = 1.dp)
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .padding(horizontal = 4.dp, vertical = 1.dp)
            .size(22.dp), contentAlignment = Alignment.Center
    ) {
        GlideImage(
            model = pinURL,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            transition = CrossFade,
            loading = placeholder(
                R.drawable.question
            ),
            failure = placeholder(R.drawable.question)
        )
    }
}