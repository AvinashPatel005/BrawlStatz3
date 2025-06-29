package com.kal.brawlstatz3.util

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kal.brawlstatz3.R

@Composable
fun LoadingBar(
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxSize()
    ) {
        val transition = rememberInfiniteTransition()
        val startOffsetX by transition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000)
            )
        )
        Image(painter = painterResource(id = R.drawable.logo_icon), contentDescription =null ,
            Modifier.size(100.dp).align(
            Alignment.Center).rotate(startOffsetX))
    }

}