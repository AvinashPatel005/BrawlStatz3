package com.kal.brawlstatz3.feature.meta.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kal.brawlstatz3.R
import com.kal.brawlstatz3.feature.brawlers.viewmodel.BrawlersViewModel
import com.kal.brawlstatz3.feature.meta.ui.components.MetaCard
import com.kal.brawlstatz3.feature.meta.ui.components.OutlinedGlideImage
import com.kal.brawlstatz3.feature.profile.viewmodel.ProfileViewModel
import com.kal.brawlstatz3.util.FirebaseStorageUtil
import com.kal.brawlstatz3.util.LoadingBar

@Composable
fun MetaScreen(brawlersViewModel: BrawlersViewModel, modifier: Modifier = Modifier) {
    if (brawlersViewModel.isLoading.value) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            LoadingBar()
        }
    }
    LazyColumn {

        for (tier in brawlersViewModel.metaList){
            stickyHeader {
                Row(modifier= Modifier.background(MaterialTheme.colorScheme.surface).padding(horizontal = 16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Tier", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = tier.color, fontSize = 24.sp, fontStyle = FontStyle.Italic)){
                            append(tier.name)
                        }
//                        if (tier.name!="NEW"){
//                            withStyle(style = SpanStyle( fontSize = 12.sp, fontStyle = FontStyle.Italic)){
//                                append("TIER")
//                            }
//                        }
                    }, modifier = Modifier.padding(horizontal = 4.dp))
                }
            }
            items(tier.brawlers, key = {it.id}){brawler->
                Surface (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                ){
                    MetaCard(brawler = brawler, rank = brawlersViewModel.sortedMetaList.indexOf(brawler)+1, isPromoted = if(brawler.tier.size>1) brawler.tier[0][0]<brawler.tier[1][0] else false,showRank = tier.name!="NEW")
                }
            }
            item{
                Spacer(modifier= Modifier.padding(vertical = 10.dp))
            }
        }
    }
}