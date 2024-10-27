package com.kal.brawlstatz3.feature.brawlers.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kal.brawlstatz3.feature.brawlers.ui.components.BrawlerCard
import com.kal.brawlstatz3.feature.brawlers.viewmodel.BrawlersViewModel

@Composable
fun BrawlersScreen(brawlersViewModel: BrawlersViewModel,modifier: Modifier = Modifier) {
    Column {
        if (brawlersViewModel.isLoading.value) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
        else{
            LazyColumn {
                items(brawlersViewModel.brawlerlist) { brawler ->
                    val isExpanded = brawlersViewModel.expandedCardID.intValue == brawler.id
                    BrawlerCard(
                        brawler = brawler,
                        traitText = brawlersViewModel.traits[brawler.trait].toString(),
                        isExpanded = isExpanded,
                        info = brawlersViewModel.info.value,
                        onClick = { brawlerUiEvent ->
                            brawlersViewModel.onEvent(brawlerUiEvent)
                        },
                        modifier = Modifier.animateItem()
                    )
                }
            }
        }

    }
}