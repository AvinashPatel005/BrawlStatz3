package com.kal.brawlstatz3.ui.main

import com.kal.brawlstatz3.data.model.Brawler

sealed class UiEvent {
    data class OnDataReceived(val list:List<Brawler>,val map:Map<Int,Brawler>) : UiEvent()
    data class OnError(val error: String) : UiEvent()
    data object OnLoading : UiEvent()
}