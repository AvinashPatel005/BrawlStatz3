package com.kal.brawlstatz3.feature.brawlers.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.kal.brawlstatz3.R
import com.kal.brawlstatz3.feature.brawlers.BrawlerUiEvent
import com.kal.brawlstatz3.util.FirebaseStorageUtil

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageActive(
    url: String,
    isActive: Boolean,
    placeholder: Int,
    size: Dp,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        GlideImage(
            model = url,
            contentDescription = null,
            transition = CrossFade,
            loading = placeholder(placeholder),
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .size(size)
                .clickable {
                    onClick()
                })
        Box(
            modifier = Modifier
                .padding(top = 2.dp)
                .background(
                    if (isActive) Color.White else Color.Transparent,
                    RoundedCornerShape(100)
                )
                .size(6.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
    }
}