package com.kal.brawlstatz3.feature.meta.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.kal.brawlstatz3.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun OutlinedGlideImage(
    url: String,
    placeholder: Int,
    size: Dp,
    strokeWidth: Dp,
    strokeColor: Color,
    radius: Dp,
    modifier: Modifier = Modifier
) {

    GlideImage(
        model = url,
        modifier = modifier
            .size(size)
            .border(
                strokeWidth,
                strokeColor, RoundedCornerShape(radius)
            )
            .padding(1.dp)
            .clip(RoundedCornerShape(radius)),
        contentDescription = null,
        loading = placeholder(placeholder), transition = CrossFade,
        failure = placeholder(R.drawable.placeholder1)
    )

}