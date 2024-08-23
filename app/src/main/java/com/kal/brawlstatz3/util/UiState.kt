package com.kal.brawlstatz3.util

import com.kal.brawlstatz3.data.model.Brawler

sealed class UiState {
    data class Success(val list: List<Brawler>, val map: Map<Int,Brawler>) : UiState()
    data class Error(val message: String) : UiState()
    data object Loading : UiState()
}