package com.kal.brawlstatz3.feature.brawlers.ui.components

import android.graphics.drawable.RippleDrawable
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kal.brawlstatz3.R
import com.kal.brawlstatz3.data.model.brawler.Brawler
import com.kal.brawlstatz3.feature.brawlers.BrawlerUiEvent
import com.kal.brawlstatz3.util.FirebaseStorageUtil
import com.kal.brawlstatz3.util.getRarityColor

@Composable
fun BrawlerCard(
    brawler: Brawler,
    traitText:String,
    isExpanded: Boolean,
    onClick:(BrawlerUiEvent)->Unit,
    modifier: Modifier = Modifier,
) {
    var componentWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    Card(
        border = BorderStroke(2.dp, getRarityColor(brawler.rarity)),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp, 2.dp)
            .onGloballyPositioned {
                componentWidth = with(density) {
                    it.size.width.toDp()
                }
            }
            .clip(CardDefaults.shape)
            .clickable(
                indication = ripple(radius = componentWidth ),
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onClick(BrawlerUiEvent.CardClicked(brawler.id)) })
            .animateContentSize(),
    ) {

        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            ,modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
                .height(if (isExpanded) 100.dp else 80.dp)
        ) {
            OutlinedGlideImage(size = if(isExpanded) 100.dp else 80.dp, url = FirebaseStorageUtil().getBrawlerProfileURL(brawler.id), placeholder = R.drawable.placeholder1,strokeWidth = 2.dp, strokeColor = Color.Black, radius = 10.dp )
            Column(modifier =
            Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .weight(1f)) {
                Text(text = brawler.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                Text(text = brawler.rarity , fontSize = 14.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, color = getRarityColor(brawler.rarity))
                if(brawler.trait!=null){
                    TraitBox(traitURL = FirebaseStorageUtil().getTraitURL(brawler.trait), traitText = traitText, isExpanded = isExpanded)
                }

            }
            if (brawler.counters[0]!=0){
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "COUNTERS", fontSize = 12.sp)
                    Row{
                        brawler.counters.forEach { pin ->
                            PinBox(pinURL = FirebaseStorageUtil().getNeutralPin(pin), backgroundColor = Color.White)
                        }
                    }
                }
            }
        }
        if(isExpanded){
            Column(
                Modifier
                    .height(200.dp)
                    .padding(horizontal = 6.dp)) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .border(
                            2.dp,
                            MaterialTheme.colorScheme.onBackground,
                            RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 4.dp, vertical = 2.dp)){
                    Text(text = brawler.about, textAlign = TextAlign.Center , lineHeight =12.sp , fontStyle = FontStyle.Italic ,fontSize = 13.sp)
                }
            }

        }
    }
}