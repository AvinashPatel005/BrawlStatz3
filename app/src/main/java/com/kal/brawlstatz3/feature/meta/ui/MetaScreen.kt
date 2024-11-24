package com.kal.brawlstatz3.feature.meta.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kal.brawlstatz3.R
import com.kal.brawlstatz3.feature.brawlers.viewmodel.BrawlersViewModel
import com.kal.brawlstatz3.feature.meta.ui.components.MetaCard
import com.kal.brawlstatz3.feature.meta.ui.components.OutlinedGlideImage
import com.kal.brawlstatz3.feature.profile.viewmodel.ProfileViewModel
import com.kal.brawlstatz3.util.FirebaseStorageUtil

@Composable
fun MetaScreen(brawlersViewModel: BrawlersViewModel, modifier: Modifier = Modifier) {
    LazyColumn {
        for (tier in brawlersViewModel.metaList){
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp),
                    color = tier.color,
                ){
                    Card(
                        shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp),
                        modifier = Modifier
                            .padding(top = 2.dp, start = 2.dp, end = 2.dp)
                            .fillMaxWidth()
                    ){
                        Text(text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = tier.color, fontSize = 20.sp, fontStyle = FontStyle.Italic)){
                                append(tier.name)
                            }
                            if (tier.name!="NEW"){
                                withStyle(style = SpanStyle( fontSize = 8.sp, fontStyle = FontStyle.Italic)){
                                    append("TIER")
                                }
                            }
                        }, modifier = Modifier.padding(horizontal = 4.dp))
                    }
                }
            }
            items(tier.brawlers, key = {it.id}){brawler->
                Surface (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    shape = RectangleShape,
                    color = tier.color,
                ){
                    MetaCard(brawler = brawler, rank = brawlersViewModel.sortedMetaList.indexOf(brawler)+1, isPromoted = brawler.tier[0][0]<brawler.tier[1][0],showRank = tier.name!="NEW")
                }
            }
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp),
                    color = tier.color,
                ) {
                    Card(
                        shape = RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp),
                        modifier = Modifier
                            .height(12.dp)
                            .padding(bottom = 2.dp, start = 2.dp, end = 2.dp)
                            .fillMaxWidth()
                    ){
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}