package com.kal.brawlstatz3.ui.brawlers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.kal.brawlstatz3.data.model.Brawler
import com.kal.brawlstatz3.data.model.ExpandableCardModel
import com.kal.brawlstatz3.ui.brawlers.components.BrawlerCard
import com.kal.brawlstatz3.ui.main.ViewModel

@Composable
fun BrawlersScreen(modifier: Modifier = Modifier, brawlersViewModel: BrawlersViewModel= hiltViewModel(), viewModel: ViewModel = hiltViewModel()) {
    Column{
        LazyColumn{
            item{
                OutlinedTextField(value = "sds", onValueChange = {})
            }
            items(viewModel.brawlerlist){ brawler ->
                val countersList = ArrayList<Brawler>()
                brawler.counters.forEach{id->
                    viewModel.brawlerMap[id]?.let { countersList.add(it) }
                }
                val isExpanded = (brawlersViewModel.expandedCard.value.isExpanded && brawlersViewModel.expandedCard.value.id == brawler.name)
                BrawlerCard(brawler = brawler, isExpanded = isExpanded, traitText = viewModel.traits[brawler.trait].toString(), counterList = countersList, onClick = {
                    if(isExpanded) brawlersViewModel.expandedCard.value = ExpandableCardModel(brawler.name, false)
                    else brawlersViewModel.expandedCard.value = ExpandableCardModel(brawler.name, true)
                })
            }
        }
    }
}