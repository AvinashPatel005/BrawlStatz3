package com.kal.brawlstatz3.feature.brawlers.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.kal.brawlstatz3.R
import com.kal.brawlstatz3.data.model.brawler.BestBuild
import com.kal.brawlstatz3.data.model.brawler.Brawler
import com.kal.brawlstatz3.data.model.brawler.NameDescription
import com.kal.brawlstatz3.feature.brawlers.BrawlerUiEvent
import com.kal.brawlstatz3.util.FirebaseStorageUtil
import com.kal.brawlstatz3.util.getRarityColor

@Composable
fun BrawlerCard(
    brawler: Brawler,
    traitText: String,
    isExpanded: Boolean,
    info: NameDescription,
    onClick: (BrawlerUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    var componentWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    Card(
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
                indication = ripple(radius = componentWidth),
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onClick(BrawlerUiEvent.CardClicked(brawler.id)) })
            .animateContentSize(),
        colors = CardDefaults.cardColors().copy(containerColor = if(isExpanded) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.background)
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .padding(horizontal = 6.dp, vertical = 4.dp)
                .fillMaxWidth()
                .height(if (isExpanded) 100.dp else 80.dp)
        ) {
            OutlinedGlideImage(
                size = if (isExpanded) 100.dp else 80.dp,
                url = FirebaseStorageUtil().getBrawlerProfileURL(brawler.id),
                placeholder = R.drawable.placeholder1,
                strokeWidth = 2.dp,
                strokeColor = Color.Black,
                radius = 10.dp
            )
            Column(
                modifier =
                Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .weight(1f)
            ) {
                Text(
                    text = brawler.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = brawler.rarity,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = getRarityColor(brawler.rarity)
                )
                if (brawler.trait != null) {
                    TraitBox(
                        traitURL = FirebaseStorageUtil().getTraitURL(brawler.trait),
                        traitText = traitText,
                        isExpanded = isExpanded
                    )
                }
            }
            if (brawler.counters[0] != 0) {
                Column(horizontalAlignment = Alignment.CenterHorizontally ) {
                    Text(text = "️COUNTERS", fontSize = 12.sp)
                    Row(modifier= Modifier.clip(RoundedCornerShape(16.dp)).background(color=if(isExpanded) MaterialTheme.colorScheme.surfaceBright else MaterialTheme.colorScheme.surfaceVariant ).padding(vertical = 4.dp, horizontal = 4.dp)) {
                        brawler.counters.forEach { pin ->
                            PinBox(
                                pinURL = FirebaseStorageUtil().getNeutralPin(pin),
                                backgroundColor = Color.Transparent
                            )
                        }
                    }
                }
            }
        }
        if (isExpanded) {
            Column(
                Modifier
                    .padding(horizontal = 6.dp)
            ) {
                Text(
                    text = brawler.about,
                    textAlign = TextAlign.Center,
                    lineHeight = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontStyle = FontStyle.Italic,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.outline,
                            RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                )

                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    MoveDescription(
                        value = brawler.attack.name,
                        header = "ATTACK",
                        icon = R.drawable.icon_attack,
                        headerColor = Color(0xfffb3d7b),
                        active = info == brawler.attack,
                        onClick = {
                            onClick(BrawlerUiEvent.InfoClicked(brawler.attack))
                        }
                    )
                    MoveDescription(
                        value = brawler.attackSuper.name,
                        header = "SUPER",
                        icon = R.drawable.icon_super,
                        headerColor = Color(0xfff4a41b),
                        active = info == brawler.attackSuper,
                        onClick = {
                            onClick(BrawlerUiEvent.InfoClicked(brawler.attackSuper))
                        }
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        brawler.gadgets.forEachIndexed { index, gadget ->
                            ImageActive(
                                url = FirebaseStorageUtil().getGadgetURL(brawler.id, index),
                                isActive = info == gadget,
                                placeholder = R.drawable.icon_gadget,
                                size = 40.dp,
                                onClick = {
                                    onClick(BrawlerUiEvent.InfoClicked(gadget))
                                }
                            )
                            if (index < brawler.gadgets.size - 1)
                                Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                    if (brawler.hypercharge != null) {
                        ImageActive(
                            url = FirebaseStorageUtil().getHyperChargeURL(brawler.id),
                            isActive = info == brawler.hypercharge,
                            placeholder = R.drawable.icon_hypercharge_blank,
                            size = 56.dp,
                            onClick = {
                                onClick(BrawlerUiEvent.InfoClicked(brawler.hypercharge))
                            })
                    } else {
                        Column {
                            Image(
                                painter = painterResource(id = R.drawable.icon_hypercharge),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(56.dp)
                            )
                            Spacer(modifier = modifier.height(6.dp))
                        }
                    }
                    Row {
                        brawler.starpowers.forEachIndexed { index, starpower ->
                            ImageActive(
                                url = FirebaseStorageUtil().getStarPowerURL(brawler.id, index),
                                isActive = info == starpower,
                                placeholder = R.drawable.icon_starpower,
                                size = 40.dp,
                                onClick = {
                                    onClick(BrawlerUiEvent.InfoClicked(starpower))
                                }
                            )
                            if (index < brawler.starpowers.size - 1)
                                Spacer(modifier = Modifier.width(4.dp))
                        }
                    }

                }
                AnimatedVisibility(visible = info != NameDescription()) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(info.name.uppercase() + ": ")
                            }
                            append(info.description)
                        },
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary,
                        lineHeight = 12.sp,
                        fontStyle = FontStyle.Italic,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}