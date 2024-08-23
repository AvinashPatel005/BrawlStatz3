package com.kal.brawlstatz3.ui.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.brawlstatz3.data.model.Brawler
import com.kal.brawlstatz3.data.repository.BrawlerRepository
import com.kal.brawlstatz3.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val repository: BrawlerRepository
) :ViewModel() {
    private val _uiState = Channel<UiState>()
    val uiEvent = _uiState.receiveAsFlow()

    var isLoading = mutableStateOf(false)
    var errorLog = mutableStateOf("")

    var list = mutableStateListOf<Brawler>()
    var map = mapOf<Int,Brawler>()

    init {
        viewModelScope.launch {
            repository.getBrawlerList(_uiState)
        }
    }

    fun onUiEvent(uiEvent: UiEvent){
        when(uiEvent){
            is UiEvent.OnDataReceived -> {
                isLoading.value = false
                list.clear()
                list.addAll(uiEvent.list)
                map = uiEvent.map
            }
            is UiEvent.OnError -> {
                isLoading.value = false
                errorLog.value = uiEvent.error
            }
            is UiEvent.OnLoading -> {
                isLoading.value = true
            }
        }
    }

    private fun sendUiEvent(event: UiState){
        viewModelScope.launch {
            _uiState.send(event)
        }
    }
}