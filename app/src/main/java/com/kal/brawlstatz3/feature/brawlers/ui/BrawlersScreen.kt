package com.kal.brawlstatz3.feature.brawlers.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.kal.brawlstatz3.feature.brawlers.BrawlerUiEvent
import com.kal.brawlstatz3.feature.brawlers.ui.components.BrawlerCard
import com.kal.brawlstatz3.feature.brawlers.viewmodel.BrawlersViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BrawlersScreen(modifier: Modifier = Modifier, brawlersViewModel: BrawlersViewModel) {
    Column {
        Text(text = brawlersViewModel.brawlerlist.toString())
        Text(text = brawlersViewModel.brawlerMap.toString())
        Text(text = brawlersViewModel.traits.toString())
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