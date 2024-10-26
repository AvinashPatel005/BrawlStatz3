package com.kal.brawlstatz3.feature.brawlers.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.brawlstatz3.data.model.brawler.Brawler
import com.kal.brawlstatz3.data.model.brawler.NameDescription
import com.kal.brawlstatz3.data.repository.BrawlerRepository
import com.kal.brawlstatz3.feature.brawlers.BrawlerUiEvent
import com.kal.brawlstatz3.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrawlersViewModel @Inject constructor(
    private val repository: BrawlerRepository
) :ViewModel() {
    val expandedCardID = mutableIntStateOf(-1)
    var traits = mutableMapOf<String,Any>()
    var isLoading = mutableStateOf(false)
    var errorLog = mutableStateOf("")

    var brawlerlist = mutableStateListOf<Brawler>()
    var brawlerMap = mapOf<Int, Brawler>()
    var info = mutableStateOf(NameDescription())

    init {
        getTraits()
        getBrawlerList()
    }

    fun onEvent(event: BrawlerUiEvent){
        when(event){
            is BrawlerUiEvent.CardClicked -> {
                expandedCardID.intValue = if (expandedCardID.intValue == event.id) -1 else event.id
                info.value = NameDescription()
            }
            is BrawlerUiEvent.InfoClicked -> {
                info.value = event.info
            }
        }
    }

    private fun getBrawlerList() = viewModelScope.launch {
        repository.getBrawlerList().collect{ response ->
            when(response){
                is Response.Failure -> {
                    isLoading.value = false
                    errorLog.value = response.e?.message.toString()
                }
                is Response.Loading -> {
                    isLoading.value = true
                }
                is Response.Success -> {
                    isLoading.value=false
                    brawlerlist.clear()
                    response.data.let { map ->
                        brawlerMap = map
                        brawlerlist.addAll(map.values.sortedBy { it.name })
                    }
                }
            }
        }
    }
    private fun getTraits() = viewModelScope.launch {
        repository.getTraitList().let { response->
            when(response){
                is Response.Failure -> {
                    errorLog.value = response.e?.message.toString()
                }
                is Response.Loading -> {

                }
                is Response.Success -> {
                    traits = response.data!!
                }
            }
        }
    }
}
