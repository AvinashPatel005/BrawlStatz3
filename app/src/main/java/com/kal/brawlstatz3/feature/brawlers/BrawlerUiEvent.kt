package com.kal.brawlstatz3.feature.brawlers

import com.kal.brawlstatz3.data.model.brawler.NameDescription

sealed class BrawlerUiEvent {
    data class CardClicked(val id: Int) : BrawlerUiEvent()
    data class InfoClicked(val info: NameDescription) : BrawlerUiEvent()
    data object SearchToggled : BrawlerUiEvent()
    data class SearchQueryChanged(val query: String) : BrawlerUiEvent()
}