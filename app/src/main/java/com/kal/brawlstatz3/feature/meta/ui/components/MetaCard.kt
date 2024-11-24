package com.kal.brawlstatz3.feature.meta.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.kal.brawlstatz3.R
import com.kal.brawlstatz3.data.model.brawler.BestBuild
import com.kal.brawlstatz3.data.model.brawler.Brawler
import com.kal.brawlstatz3.data.model.brawler.NameDescription
import com.kal.brawlstatz3.util.FirebaseStorageUtil
import com.kal.brawlstatz3.util.getRarityColor

@Preview
@Composable
private fun Test() {
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
            NameDescription(
                name = "CLONE PROJECTOR",
                description = "Leon creates an illusion of himself to confuse his enemies."
            ),
            NameDescription(
                name = "LOLLIPOP DROP",
                description = "Leon creates a stealthy area for his team to be in. The lollipop slowly loses its health over time."
            )
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
            NameDescription(
                name = "SMOKE TRAILS",
                description = "When Leon uses his Super, he gains a 30% boost to his movement speed for the duration of his invisibility."
            ),
            NameDescription(
                name = "INVISIHEAL",
                description = "Leon recovers 1000 health per second while his Super is active."
            )
        ),
        tier = listOf("01","A07"),
        type = "Assassin",
        version = 0
    )
    MetaCard(brawler = brawler,1,true ,true)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MetaCard(brawler: Brawler,rank:Int,isPromoted:Boolean,showRank:Boolean,modifier: Modifier = Modifier) {
    Card(
        shape = RectangleShape,
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .fillMaxWidth()
    ){
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp, horizontal = 4.dp,)) {
            Row (verticalAlignment = Alignment.CenterVertically){
                Box{
                    OutlinedGlideImage(
                        url = FirebaseStorageUtil().getBrawlerProfileURL(brawler.id),
                        placeholder = R.drawable.placeholder2 ,
                        size = 80.dp,
                        strokeWidth = 2.dp ,
                        strokeColor = Color.Black,
                        radius = 10.dp
                    )
                    if (brawler.hypercharge!=null){
                        GlideImage(model = FirebaseStorageUtil().getHyperChargeURL(brawler.id) , contentDescription = null, modifier = Modifier
                            .height(35.dp)
                            .align(
                                Alignment.BottomStart
                            ))
                    }
                }
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(
                        text = brawler.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)

                    Row(verticalAlignment = Alignment.CenterVertically){
                        Box(contentAlignment = Alignment.Center){
                            Image(
                                painter = painterResource(id = R.drawable.icon_gadget),
                                contentDescription = null,
                                modifier=Modifier.height(20.dp)
                            )
                            Text(text = (brawler.bestBuild.starpower+1).toString(), fontSize = 10.sp ,fontWeight = FontWeight.Bold, color = Color.White)
                        }
                        Spacer(modifier = Modifier.width(2.dp))
                        Box(contentAlignment = Alignment.Center){
                            Image(
                                painter = painterResource(id = R.drawable.icon_starpower),
                                contentDescription = null,
                                modifier=Modifier.height(20.dp)
                            )
                            Text(text = (brawler.bestBuild.starpower+1).toString(), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        brawler.bestBuild.gears.forEach {
                            GlideImage(model = FirebaseStorageUtil().getGearURL(it), contentDescription = null , modifier= Modifier
                                .height(20.dp)
                                .padding(vertical = 1.dp))
                            Spacer(modifier = Modifier.width(2.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
            if(showRank){
                Column(horizontalAlignment = Alignment.End,verticalArrangement = Arrangement.Center){
                    if(isPromoted) Image(painter = painterResource(id = R.drawable.icon_promote), contentDescription = null, modifier = Modifier.width(16.dp).padding(end = 6.dp))
                    Text(text = "#${rank}")
                }
            }
        }
    }
}
