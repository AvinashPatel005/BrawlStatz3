package com.kal.brawlstatz3.feature.brawlers

sealed class BrawlerUiEvent {
    data class CardClicked(val id: Int) : BrawlerUiEvent()
}