package com.kal.brawlstatz3.data.repository

import com.kal.brawlstatz3.util.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel

interface BrawlerRepository {
    fun getBrawlerList(uiState: Channel<UiState>)
}