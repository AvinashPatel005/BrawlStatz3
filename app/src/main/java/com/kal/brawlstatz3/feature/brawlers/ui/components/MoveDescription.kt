package com.kal.brawlstatz3.feature.brawlers.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kal.brawlstatz3.R
import com.kal.brawlstatz3.util.balooFamily


@Composable
fun MoveDescription(
    moveName: String,
    previewText: String,
    previewIcon: Int,
    previewColor: Color,
    clicked: Boolean,
) {
    Box {
        Box(
            modifier = Modifier.height(35.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)
                    .clip(
                        RoundedCornerShape(
                            topEnd = 4.dp,
                            bottomEnd = 4.dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer)

            )
        }
        Column(
            modifier = Modifier
                .height(35.dp)
                .padding(start = 43.dp),
        ) {
            Text(
                previewText,
                fontSize = 12.sp,
                color = previewColor,
                fontFamily = balooFamily,
                fontWeight = FontWeight.ExtraBold,
                style = TextStyle(
                    shadow = Shadow(offset = Offset(1f, 1f), blurRadius = 1f)
                )
            )
            Text(
                moveName.uppercase(),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.offset(y = (-4).dp)
            )
        }
        Image(
            painter = painterResource(id = previewIcon),
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .background(Color.Transparent)
        )
        if (clicked) {
            Image(
                painter = painterResource(id = R.drawable.icon_overlay),
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .background(Color.Transparent)
            )

        }
    }
}


