package com.kal.brawlstatz3.feature.brawlers.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kal.brawlstatz3.R
import com.kal.brawlstatz3.feature.brawlers.BrawlerUiEvent
import com.kal.brawlstatz3.feature.brawlers.ui.components.BrawlerCard
import com.kal.brawlstatz3.feature.brawlers.viewmodel.BrawlersViewModel
import com.kal.brawlstatz3.util.LoadingBar

@Composable
fun BrawlersScreen(brawlersViewModel: BrawlersViewModel,modifier: Modifier = Modifier) {
    Column {
        if (brawlersViewModel.isLoading.value) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                LoadingBar()
            }
        }
        else{

            LazyColumn {
                item{
                    LazyRow(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .height(35.dp)
                    ) {
                        item { Spacer(modifier = Modifier.width(8.dp)) }
                        itemsIndexed(brawlersViewModel.filterList) {index,filter  ->
                            Spacer(modifier = Modifier.width(10.dp))
                            Button(
                                modifier =
                                    Modifier.border(1.dp,MaterialTheme.colorScheme.primary,
                                        RoundedCornerShape(12.dp)).clip(RoundedCornerShape(12.dp)).height(30.dp),
                                onClick = {
                                    brawlersViewModel.onEvent(BrawlerUiEvent.FilterToggled(index))
                                },
                                colors = if (index == brawlersViewModel.filter.intValue) ButtonDefaults.buttonColors(
                                    MaterialTheme.colorScheme.primary
                                ) else ButtonDefaults.buttonColors(
                                    MaterialTheme.colorScheme.surfaceVariant
                                ),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
                            ) {
                                Text(
                                    text = filter,
                                    fontSize = 14.sp,
                                    color = if (index == brawlersViewModel.filter.intValue) MaterialTheme.colorScheme.onPrimary
                                    else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }
                }
                items(brawlersViewModel.brawlerlist) { brawler ->
                    val isExpanded = brawlersViewModel.expandedCardID.intValue == brawler.id
                    BrawlerCard(
                        brawler = brawler,
                        traitText = brawlersViewModel.traits[brawler.trait].toString(),
                        isExpanded = isExpanded,
                        info = brawlersViewModel.info.value,
                        onClick = brawlersViewModel::onEvent,
                        modifier = Modifier.animateItem()
                    )
                }
                item{
                    Text(text = "Copyright © ATeamDiversity", textAlign = TextAlign.Center, modifier = Modifier
                        .alpha(0.5f)
                        .fillMaxWidth()
                        .height(220.dp), fontSize = 14.sp)
                }
            }
        }

    }
}