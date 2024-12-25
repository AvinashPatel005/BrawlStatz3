package com.kal.brawlstatz3.feature.meta.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.RichTooltipColors
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.kal.brawlstatz3.R
import com.kal.brawlstatz3.data.model.brawler.BestBuild
import com.kal.brawlstatz3.data.model.brawler.Brawler
import com.kal.brawlstatz3.data.model.brawler.NameDescription
import com.kal.brawlstatz3.util.FirebaseStorageUtil
import com.kal.brawlstatz3.util.getRarityColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MetaCard(brawler: Brawler,rank:Int,isPromoted:Boolean,showRank:Boolean,modifier: Modifier = Modifier) {
    val gadgetToolTip = rememberTooltipState(isPersistent =true)
    val starpowerToolTip = rememberTooltipState(isPersistent =true)
    val scope = rememberCoroutineScope()
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
                        TooltipBox(positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(), tooltip = {
                            RichTooltip(
                                title = {
                                    Row{
                                        Text(text = brawler.starpowers[brawler.bestBuild.starpower].name)

                                        Spacer(modifier = Modifier.width(4.dp))
                                        GlideImage(model = FirebaseStorageUtil().getStarPowerURL(brawler.id,brawler.bestBuild.starpower), contentDescription =null, modifier = Modifier.height(20.dp) )
                                    }
                                    },
                                text = { Text(text = brawler.starpowers[brawler.bestBuild.starpower].description) },
                                modifier = Modifier.fillMaxWidth(0.5f),
                                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomEnd = 10.dp, bottomStart = 0.dp),
                                shadowElevation = 2.dp
                                )
                        }, state = starpowerToolTip) {
                            Box(contentAlignment = Alignment.Center){
                                Image(
                                    painter = painterResource(id = if(brawler.bestBuild.starpower==-1) R.drawable.icon_starpower_default else R.drawable.icon_starpower),
                                    contentDescription = null,
                                    modifier= Modifier
                                        .height(20.dp)
                                        .clip(RoundedCornerShape(50))
                                        .clickable {
                                            scope.launch {
                                                if (brawler.bestBuild.starpower != -1)
                                                    starpowerToolTip.show()
                                            }
                                        }
                                )
                                if(brawler.bestBuild.starpower!=-1)
                                    Text(text = (brawler.bestBuild.starpower+1).toString(), fontSize = 10.sp ,fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }



                        Spacer(modifier = Modifier.width(2.dp))

                        TooltipBox(positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(), tooltip = {
                            RichTooltip(
                                title = {
                                    Row {
                                        Text(text = brawler.gadgets[brawler.bestBuild.gadget].name)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        GlideImage(model = FirebaseStorageUtil().getGadgetURL(brawler.id,brawler.bestBuild.gadget), contentDescription =null, modifier = Modifier.height(20.dp) )
                                    }
                                },
                                text = { Text(text = brawler.gadgets[brawler.bestBuild.gadget].description) },
                                modifier = Modifier.fillMaxWidth(0.5f),
                                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomEnd = 10.dp, bottomStart = 0.dp),
                                shadowElevation = 2.dp,
                            )
                        }, state = gadgetToolTip ) {
                            Box(contentAlignment = Alignment.Center){
                                Image(
                                    painter = painterResource(id = if(brawler.bestBuild.gadget==-1) R.drawable.icon_gadget_default else R.drawable.icon_gadget),
                                    contentDescription = null,
                                    modifier= Modifier
                                        .height(20.dp)
                                        .clip(RoundedCornerShape(50))
                                        .clickable {
                                            if (brawler.bestBuild.gadget != -1)
                                                scope.launch { gadgetToolTip.show() }
                                        }
                                )
                                if(brawler.bestBuild.gadget!=-1)
                                    Text(text = (brawler.bestBuild.gadget+1).toString(), fontSize = 10.sp ,fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }

                        Spacer(modifier = Modifier.width(6.dp))
                        brawler.bestBuild.gears.forEach {
                            GlideImage(model = FirebaseStorageUtil().getGearURL(it), contentDescription = null , modifier= Modifier
                                .height(20.dp)
                                .padding(vertical = 1.dp))
                            Spacer(modifier = Modifier.width(2.dp))
                        }
                    }
                }
            }
            if(showRank){
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                    Text(text = "#${rank}")
                    Spacer(modifier = Modifier.width(2.dp))
                    if(isPromoted) Image(painter = painterResource(id = R.drawable.icon_promote), contentDescription = null, modifier = Modifier
                        .width(8.dp))
                    else Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}
