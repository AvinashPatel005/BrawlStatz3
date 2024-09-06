package com.kal.brawlstatz3.ui.brawlers.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.kal.brawlstatz3.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TraitBox(traitURL:String,traitText: String,isExpanded:Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier
        .background(
            if(isExpanded) MaterialTheme.colorScheme.inverseSurface else Color.Transparent,
            RoundedCornerShape(8.dp)
        )
        .padding(horizontal = if (isExpanded) 2.dp else 0.dp )){
        GlideImage(model = traitURL, contentDescription =null, modifier = Modifier
            .size(16.dp)
            .background(
                Color.Black, RoundedCornerShape(6.dp)
            )
            .padding(1.dp))
        Spacer(modifier = Modifier.width(2.dp))
        if(isExpanded){
            Text(text = traitText, color = MaterialTheme.colorScheme.inverseOnSurface, fontWeight = FontWeight.Bold, fontSize = 10.sp, lineHeight = 10.sp,modifier = Modifier.clip(
                RoundedCornerShape(8.dp)).background(Color.White).defaultMinSize(minHeight = 20.dp).wrapContentHeight(align = Alignment.CenterVertically))
        }
    }
}