package com.kal.brawlstatz3.feature.profile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.kal.brawlstatz3.R
import com.kal.brawlstatz3.data.model.brawler.Brawler
import com.kal.brawlstatz3.feature.profile.ui.components.BattleCard
import com.kal.brawlstatz3.feature.profile.ui.components.StatCard
import com.kal.brawlstatz3.feature.profile.ui.components.StatHeader
import com.kal.brawlstatz3.feature.profile.viewmodel.ProfileViewModel
import com.kal.brawlstatz3.util.FirebaseStorageUtil


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel,brawlerList:List<Brawler> ,modifier: Modifier = Modifier) {
    val profile = profileViewModel.profile.value
    val club = profileViewModel.club.value
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth / 2
    val favorite = brawlerList.find { brawler->brawler.name == profile.favourite }
    if(profileViewModel.isLoading.value){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }
    else{
        LazyColumn(modifier = Modifier.padding(horizontal = 8.dp)){

            item{
                Card(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 0.dp, bottomEnd = 20.dp, bottomStart = 0.dp)){
                    Box{
                        if (favorite != null) {
                            GlideImage(model = FirebaseStorageUtil().getBrawlerModel2DURL(favorite.id), contentDescription = null, modifier = Modifier
                                .align(
                                    Alignment.CenterEnd
                                )
                                .size(200.dp)
                                .alpha(0.4f))
                        }
                        Row (verticalAlignment = Alignment.CenterVertically,modifier= Modifier
                            .fillMaxWidth()
                            .height(200.dp)){
                            Spacer(modifier = Modifier.width(8.dp))
                            GlideImage(model = profile.dp, contentDescription = null, modifier = Modifier.size(100.dp) )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(text = profile.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                                Row (verticalAlignment = Alignment.CenterVertically){
                                    GlideImage(model = club.dp, contentDescription = null, modifier = Modifier.size(18.dp) )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(text = club.name, fontSize = 12.sp)
                                }
                                profile.fame?.let { GlideImage(model = it.icon, contentDescription = it.value , modifier = Modifier.size(36.dp)) }
                            }
                        }
                        profile.accountCreated?.let { Text(text = it, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold, modifier = Modifier
                            .padding(top = 12.dp)
                            .align(Alignment.TopEnd)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(
                                    topStart = 8.dp,
                                    bottomEnd = 0.dp,
                                    topEnd = 0.dp,
                                    bottomStart = 2.dp
                                )
                            )
                            .padding(start = 6.dp, end = 2.dp), fontSize = 12.sp) }
                        Row (modifier = Modifier
                            .padding(bottom = 2.dp, end = 2.dp)
                            .align(Alignment.BottomEnd)){
                            if(profile.winStreak!=null){
                                Row(verticalAlignment = Alignment.CenterVertically){
                                    GlideImage(model = profile.winStreak.icon, contentDescription =null, modifier = Modifier.size(30.dp) )
                                    Text(text = profile.winStreak.value, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface, style = TextStyle(shadow = Shadow(
                                        Color.Black, Offset(1f,1f), 4f)),
                                        modifier = Modifier.offset(x= (-4).dp)
                                    )
                                }
                            }
                        }

                    }
                }
            }
            item{
                StatHeader(header = "Ranked")
            }
            item{
                profile.ranked?.current?.let { StatCard(icon = it.icon, name = it.name, value = it.value) }
            }
            item{
                profile.ranked?.highest?.let { StatCard(icon = it.icon, name = it.name, value = it.value) }
            }
            item{
                StatHeader(header = "Stats")
            }

            items(profile.stats,key = {stat->stat.name}) { stat->
                StatCard(icon = stat.icon, name = stat.name, value = stat.value)
            }
            item{
                StatHeader(header = "Progress")
            }
            items(profile.progress, key = {progress->progress.name}){ progress->
                StatCard(icon = progress.icon, name = progress.name, value = progress.value)
            }
            item{
                StatHeader(header = "Battle Log ("+profile.battleLog.filter { log->log.result>0 }.size+"/"+profile.battleLog.size+")")
            }
            item {
                LazyVerticalGrid(columns = GridCells.Adaptive(50.dp), modifier = Modifier.height(130.dp), userScrollEnabled = false) {
                    itemsIndexed(profile.battleLog, key = { index, _ -> index }) { _, battle ->
                        BattleCard(battle = battle)
                    }
                }
            }
            item{
                StatHeader(header = "Brawlers")
            }
            item{
                LazyHorizontalGrid(rows = GridCells.Fixed(2), modifier = Modifier.height(250.dp)) {
                    items(profile.brawlersData, key = {brawler->brawler.name}) { brawler->
                        Column ( modifier = Modifier.width(itemWidth).padding(start = 2.dp,end = 4.dp)){
                            Box(modifier = Modifier.border(1.dp, Color.Red)){
                                Row (horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth()){
                                    GlideImage(model = brawler.dp, contentDescription = null, modifier = Modifier.size(60.dp))
                                    Column {
                                        Text(text = brawler.name)
                                        Row {
                                            brawler.gears.forEach {
                                                GlideImage(model = it, contentDescription = null, modifier = Modifier.size(18.dp))
                                            }
                                        }
                                        Row {
                                            brawler.starPower.forEach {
                                                Box(contentAlignment = Alignment.Center){
                                                    Image(painter = painterResource(id = R.drawable.icon_starpower), contentDescription = null, modifier = Modifier.size(18.dp))
                                                    GlideImage(model = it, contentDescription = null, modifier = Modifier.size(8.dp))
                                                }
                                            }
                                            brawler.gadget .forEach {
                                                Box(contentAlignment = Alignment.Center){
                                                    Image(painter = painterResource(id = R.drawable.icon_gadget), contentDescription = null, modifier = Modifier.size(18.dp))

                                                    GlideImage(model = it, contentDescription = null, modifier = Modifier.size(8.dp))

                                                }
                                            }
                                        }

                                    }
                                }
                            }
                            Row {
                                Column {
                                    Text(text = "Highest")
                                    Text(text = brawler.highestTrophy)
                                }
                                Column {
                                    Text(text = "Current")
                                    Text(text = brawler.currentTrophy)
                                }
                                Column {
                                    Text(text = "Level")
                                    Text(text = brawler.powerLevel)
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}