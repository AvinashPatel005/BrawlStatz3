package com.kal.brawlstatz3.feature.brawlers.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
    value: String,
    header: String,
    icon: Int,
    headerColor: Color,
    active: Boolean,
    onClick: () -> Unit
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
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable {
                        onClick()
                    }

            )
        }
        Column(
            modifier = Modifier
                .height(35.dp)
                .padding(start = 43.dp),
        ) {
            Text(
                header,
                fontSize = 12.sp,
                color = headerColor,
                fontFamily = balooFamily,
                fontWeight = FontWeight.ExtraBold,
                style = TextStyle(
                    shadow = Shadow(offset = Offset(1f, 1f), blurRadius = 1f)
                )
            )
            Text(
                value.uppercase(),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,

                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.offset(y = (-4).dp)
            )
        }
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .background(Color.Transparent)
        )
        if (active) {
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


