package com.kal.brawlstatz3.feature.brawlers.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TraitBox(traitURL: String, traitText: String, isExpanded: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                if (isExpanded) MaterialTheme.colorScheme.primary else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = if (isExpanded) 2.dp else 0.dp)
    ) {
        GlideImage(
            model = traitURL,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .background(
                    Color.Black, RoundedCornerShape(6.dp)
                )
                .padding(1.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        if (isExpanded) {
            Text(
                text = traitText,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                lineHeight = 10.sp,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .defaultMinSize(minHeight = 20.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
        }
    }
}