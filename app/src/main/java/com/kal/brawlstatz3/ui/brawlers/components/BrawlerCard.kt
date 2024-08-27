package com.kal.brawlstatz3.ui.brawlers.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.kal.brawlstatz3.R
import com.kal.brawlstatz3.data.model.Brawler
import com.kal.brawlstatz3.util.FirebaseStorageUtil
import com.kal.brawlstatz3.util.getRarityColor


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BrawlerCard(
    brawler: Brawler,
    isExpanded: Boolean,
    traitText:String,
    counterList: List<Brawler>,
    onClick:()->Unit,
    modifier: Modifier = Modifier,
) {
    Card(onClick = {
        onClick()
    }, border = BorderStroke(2.dp, getRarityColor(brawler.rarity)), modifier = modifier
        .padding(8.dp, 2.dp)
        .animateContentSize()
        .fillMaxWidth()
    ) {
        Column{
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
                ,modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth()
                    .height(if (isExpanded) 120.dp else 90.dp)
            ) {
                Row{
                    OutlinedGlideImage(size = if(isExpanded) 120.dp else 90.dp, url = FirebaseStorageUtil().getBrawlerProfileURL(brawler.id), placeholder = R.drawable.placeholder1,strokeWidth = 2.dp, strokeColor = Color.Black, radius = 10.dp )
                    Column(Modifier.padding(8.dp)) {
                        Text(text = brawler.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.fillMaxWidth(0.5f))
                        Text(text = brawler.rarity , fontSize = 14.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, color = getRarityColor(brawler.rarity))
                        if(brawler.trait!=null){
                            Spacer(modifier = Modifier.height(4.dp))
                            TraitBox(traitURL = FirebaseStorageUtil().getTraitURL(brawler.trait), traitText = traitText, isExpanded = isExpanded)
                        }

                    }
                }
                val rotation by animateFloatAsState(targetValue = if (isExpanded) 0f else 1f,
                    label = "rotation"
                )
                val offset by animateFloatAsState(targetValue = if(isExpanded) 0f else 1f, animationSpec = tween(100),
                    label = "offset"
                )
                val size = animateDpAsState(targetValue = if (isExpanded) 26.dp else 30.dp, label = "size")
                Column(horizontalAlignment = Alignment.End){

                    AnimatedVisibility(visible = isExpanded) {
                        Text(text = "COUNTERS", fontSize = 14.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.SemiBold ,lineHeight = 14.sp)
                    }
                    Box(
                        Modifier
                            .fillMaxHeight(0.75f),
                    ){
                        counterList.forEachIndexed{index,counterBrawler->
                            TextImage(
                                name = counterBrawler.name,
                                profile = FirebaseStorageUtil().getNeutralPin(counterBrawler.id),
                                size=size.value,
                                nameVisibility = isExpanded ,
                                offsetX = counterAdvanceImageList[index].offsetX*offset ,
                                rotation = counterAdvanceImageList[index].rotation * rotation,
                                alignment = counterAdvanceImageList[index].alignment
                            )
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
}