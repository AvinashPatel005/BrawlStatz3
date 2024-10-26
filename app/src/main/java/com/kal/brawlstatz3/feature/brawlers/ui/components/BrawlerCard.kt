package com.kal.brawlstatz3.feature.brawlers.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BasicTooltipBox
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberBasicTooltipState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.load.model.Model
import com.kal.brawlstatz3.R
import com.kal.brawlstatz3.data.model.brawler.BestBuild
import com.kal.brawlstatz3.data.model.brawler.Brawler
import com.kal.brawlstatz3.data.model.brawler.NameDescription
import com.kal.brawlstatz3.feature.brawlers.BrawlerUiEvent
import com.kal.brawlstatz3.util.FirebaseStorageUtil
import com.kal.brawlstatz3.util.getRarityColor
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
private fun CardPreview() {
    val brawler = Brawler().copy(
        about = "Leon shoots a quick salvo of blades at his target. His Super trick is a smoke bomb that makes him invisible for a little while!",
        attack = NameDescription(
            name = "Spinner Blades",
            description = "Leon flicks his wrist and fires four Spinner Blades. The blades deal less damage the farther they travel."
        ),
        attackSuper = NameDescription(
            name = "Smoke Bomb",
            description = "Leon becomes invisible for 6 seconds. If he attacks, he will be revealed. Enemies close to Leon will be able to spot him."
        ),
        bestBuild = BestBuild(
            gadget = 1,
            gears = listOf("leong1", "sg"),
            starpower = 1
        ),
        counters = listOf(16000017, 16000012, 16000045),
        gadgets = listOf(
            NameDescription(name = "CLONE PROJECTOR", description = "Leon creates an illusion of himself to confuse his enemies."),
            NameDescription(name = "LOLLIPOP DROP", description = "Leon creates a stealthy area for his team to be in. The lollipop slowly loses its health over time.")
        ),
        hypercharge = NameDescription(
            name = "SMOKE TRAILS",
            description = "When Leon uses his Super, he gains a 30% boost to his movement speed for the duration of his invisibility."
        ),
        id = 16000023,
        mastery = "The Sneaky",
        model3d = "false",
        movementSpeed = "Very Fast",
        name = "LEON",
        rarity = "LEGENDARY",
        starpowers = listOf(
            NameDescription(name = "SMOKE TRAILS", description = "When Leon uses his Super, he gains a 30% boost to his movement speed for the duration of his invisibility."),
            NameDescription(name = "INVISIHEAL", description = "Leon recovers 1000 health per second while his Super is active.")
        ),
        tier = "A07",
        type = "Assassin",
        version = 0)
    BrawlerCard(brawler = brawler, traitText = "ability to swim", isExpanded = true , info = NameDescription() ,onClick = {})
}

@OptIn(ExperimentalGlideComposeApi::class
)
@Composable
fun BrawlerCard(
    brawler: Brawler,
    traitText:String,
    isExpanded: Boolean,
    info:NameDescription,
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
                indication = ripple(radius = componentWidth),
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
                    .padding(horizontal = 6.dp)) {
                Text(text = brawler.about, textAlign = TextAlign.Center , lineHeight =12.sp , color = MaterialTheme.colorScheme.onBackground, fontStyle = FontStyle.Italic ,fontSize = 13.sp,modifier= Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 4.dp, vertical = 2.dp))

                Column(modifier = Modifier.padding(vertical = 4.dp)){
                    MoveDescription(
                        value = brawler.attack.name,
                        header = "ATTACK",
                        icon = R.drawable.icon_attack,
                        headerColor = Color(0xfffb3d7b),
                        active = info==brawler.attack,
                        onClick = {
                            onClick(BrawlerUiEvent.InfoClicked(brawler.attack))
                        }
                    )
                    MoveDescription(
                        value = brawler.attackSuper.name,
                        header = "SUPER",
                        icon = R.drawable.icon_super,
                        headerColor = Color(0xfff4a41b),
                        active = info==brawler.attackSuper,
                        onClick = {
                            onClick(BrawlerUiEvent.InfoClicked(brawler.attackSuper))
                        }
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier
                    .fillMaxWidth()){
                    Row (verticalAlignment = Alignment.CenterVertically){
                        brawler.gadgets.forEachIndexed { index, gadget->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                GlideImage(model = FirebaseStorageUtil().getGadgetURL(brawler.id,index), loading = placeholder(R.drawable.icon_gadget), transition = CrossFade, contentDescription = null,modifier= Modifier
                                    .clip(RoundedCornerShape(100))
                                    .size(40.dp)
                                    .clickable {
                                        onClick(BrawlerUiEvent.InfoClicked(gadget))
                                    } )
                                Box(modifier = Modifier
                                    .padding(top = 2.dp)
                                    .background(
                                        if (info == gadget) Color.White else Color.Transparent,
                                        RoundedCornerShape(100)
                                    )
                                    .size(6.dp))
                            }
                            if(index<brawler.gadgets.size-1) Spacer(modifier = Modifier.width(4.dp))
                        }

                    }
                    if(brawler.hypercharge!=null){
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            GlideImage(model = FirebaseStorageUtil().getHyperChargeURL(brawler.id), contentDescription = null, loading = placeholder(R.drawable.icon_hypercharge_blank),transition = CrossFade,modifier= Modifier
                                .clip(RoundedCornerShape(100))
                                .size(56.dp)
                                .clickable {
                                    onClick(BrawlerUiEvent.InfoClicked(brawler.hypercharge))
                                } )
                            Box(modifier = Modifier
                                .padding(vertical = 2.dp)
                                .background(
                                    if (info == brawler.hypercharge) Color.White else Color.Transparent,
                                    RoundedCornerShape(100)
                                )
                                .size(6.dp))
                        }
                    }
                    else{
                        Column {
                            Image(painter = painterResource(id = R.drawable.icon_hypercharge), contentDescription = null,modifier= Modifier
                                .size(56.dp) )
                            Spacer(modifier = modifier.height(6.dp))
                        }
                    }
                    Row {
                        brawler.starpowers.forEachIndexed { index, starpower->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                GlideImage(model = FirebaseStorageUtil().getStarPowerURL(brawler.id,index), contentDescription = null, transition = CrossFade, loading = placeholder(R.drawable.icon_starpower),modifier= Modifier
                                    .clip(RoundedCornerShape(100))
                                    .size(40.dp)
                                    .clickable {
                                        onClick(BrawlerUiEvent.InfoClicked(starpower))
                                    } )
                                Box(modifier = Modifier
                                    .padding(top = 2.dp)
                                    .background(
                                        if (info == starpower) Color.White else Color.Transparent,
                                        RoundedCornerShape(100)
                                    )
                                    .size(6.dp))
                            }
                            if(index<brawler.starpowers.size-1) Spacer(modifier = Modifier.width(4.dp))
                        }
                    }

                }
                AnimatedVisibility(visible = info!=NameDescription()) {
                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                            append(info.name.uppercase()+": ")
                        }
                        append(info.description)
                    }, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onPrimary , lineHeight =12.sp , fontStyle = FontStyle.Italic ,fontSize = 13.sp,modifier= Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 4.dp, vertical = 2.dp))
                }
            }
        }
    }
}