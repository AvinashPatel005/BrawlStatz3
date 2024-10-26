package com.kal.brawlstatz3.feature.brawlers.ui
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kal.brawlstatz3.feature.brawlers.ui.components.BrawlerCard
import com.kal.brawlstatz3.feature.brawlers.viewmodel.BrawlersViewModel

@Composable
fun BrawlersScreen(modifier: Modifier = Modifier, brawlersViewModel: BrawlersViewModel) {
    Column{
        LazyColumn{
            items(brawlersViewModel.brawlerlist){ brawler ->
                val isExpanded = brawlersViewModel.expandedCardID.intValue == brawler.id
                BrawlerCard(brawler = brawler, traitText = brawlersViewModel.traits[brawler.trait].toString()  ,isExpanded = isExpanded , info = brawlersViewModel.info.value ,onClick = {brawlerUiEvent->
                    brawlersViewModel.onEvent(brawlerUiEvent)
                })
            }
        }
    }
}