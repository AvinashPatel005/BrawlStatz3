package com.kal.brawlstatz3.ui.brawlers
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kal.brawlstatz3.data.model.ExpandableCardModel
import com.kal.brawlstatz3.ui.brawlers.components.BrawlerCard
import com.kal.brawlstatz3.ui.main.ViewModel

@Composable
fun BrawlersScreen(modifier: Modifier = Modifier, brawlersViewModel: BrawlersViewModel= hiltViewModel(), viewModel: ViewModel = hiltViewModel()) {
    Column{
        LazyColumn{
            items(viewModel.brawlerList){ brawler ->
                val isExpanded = (brawlersViewModel.expandedCard.value.isExpanded && brawlersViewModel.expandedCard.value.id == brawler.id)
                BrawlerCard(brawler = brawler, traitText = viewModel.traits[brawler.trait].toString()  ,isExpanded = isExpanded , onClick = {
                    brawlersViewModel.expandedCard.value = ExpandableCardModel(brawler.id, !isExpanded)
                })
            }
        }
    }
}