package com.kal.brawlstatz3.feature.brawlers.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.brawlstatz3.data.model.brawler.Brawler
import com.kal.brawlstatz3.data.model.brawler.NameDescription
import com.kal.brawlstatz3.data.repository.BrawlerRepository
import com.kal.brawlstatz3.feature.brawlers.BrawlerUiEvent
import com.kal.brawlstatz3.util.Response
import com.kal.brawlstatz3.util.Tier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrawlersViewModel @Inject constructor(
    private val repository: BrawlerRepository
) : ViewModel() {
    val expandedCardID = mutableIntStateOf(-1)
    var traits = mutableMapOf<String, Any>()
    var isLoading = mutableStateOf(false)
    var errorLog = mutableStateOf("")

    var mainBrawlerList = ArrayList<Brawler>()
    var brawlerlist = mutableStateListOf<Brawler>()
    var brawlerMap = mapOf<Int, Brawler>()
    var info = mutableStateOf(NameDescription())

    var sortedMetaList:ArrayList<Brawler> = ArrayList()
    var metaList = mutableStateListOf<Tier>()

    var isSearchActive = mutableStateOf(false)
    var searchQuery = mutableStateOf("")

    init {
        getTraits()
        getBrawlerList()
    }
    fun getMetaList() {
        val list :ArrayList<Tier> = ArrayList()
        sortedMetaList.clear()
        sortedMetaList.addAll(mainBrawlerList.sortedBy { it.tier[0] })
        val sTier: ArrayList<Brawler> = ArrayList()
        val aTier: ArrayList<Brawler> = ArrayList()
        val bTier: ArrayList<Brawler> = ArrayList()
        val cTier: ArrayList<Brawler> = ArrayList()
        val dTier: ArrayList<Brawler> = ArrayList()
        val fTier: ArrayList<Brawler> = ArrayList()
        val nTier: ArrayList<Brawler> = ArrayList()
        for(brawler in sortedMetaList){
            when(brawler.tier[0].uppercase()[0]){
                in '0'..'9' -> sTier.add(brawler)
                'A' -> aTier.add(brawler)
                'B' -> bTier.add(brawler)
                'C' -> cTier.add(brawler)
                'D' -> dTier.add(brawler)
                'F' -> fTier.add(brawler)
                'N' -> nTier.add(brawler)
            }
        }
        if(nTier.isNotEmpty()) list.add(Tier("NEW", Color.Cyan,nTier))
        list.add(Tier("S", Color(0xFFff7e7e),sTier))
        list.add(Tier("A", Color(0xFFffbf7f),aTier))
        list.add(Tier("B", Color(0xFFffde7f),bTier))
        list.add(Tier("C", Color(0xFFfeff7f),cTier))
        list.add(Tier("D", Color(0xFFbeff7d),dTier))
        list.add(Tier("F", Color(0xFF7eff80),fTier))

        metaList.addAll(list)
    }

    fun onEvent(event: BrawlerUiEvent) {
        when (event) {
            is BrawlerUiEvent.CardClicked -> {
                expandedCardID.intValue = if (expandedCardID.intValue == event.id) -1 else event.id
                info.value = NameDescription()
            }

            is BrawlerUiEvent.InfoClicked -> {
                info.value = event.info
            }

            is BrawlerUiEvent.SearchQueryChanged -> {
                searchQuery.value = event.query
                search(searchQuery.value)
            }

            is BrawlerUiEvent.SearchToggled -> {
                if (isSearchActive.value){
                    if (searchQuery.value.isNotEmpty()){
                        searchQuery.value=""
                        search(searchQuery.value)
                    }
                    else{
                        isSearchActive.value = false
                    }
                }
                else{
                    isSearchActive.value = true
                }
            }
        }
    }

    private fun getBrawlerList() = viewModelScope.launch {
        repository.getBrawlerList().collect { response ->
            when (response) {
                is Response.Failure -> {
                    isLoading.value = false
                    errorLog.value = response.e?.message.toString()
                }

                is Response.Loading -> {
                    isLoading.value = true
                }

                is Response.Success -> {
                    isLoading.value = false
                    brawlerlist.clear()
                    mainBrawlerList.clear()
                    response.data.let { map ->
                        brawlerMap = map
                        brawlerlist.addAll(map.values.sortedBy { it.name })
                        mainBrawlerList.addAll(map.values.sortedBy { it.name })
                        getMetaList()
                    }
                }
            }
        }
    }

    private fun getTraits() = viewModelScope.launch {
        repository.getTraitList().let { response ->
            when (response) {
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

    fun search(name: String) {
        if (name.isEmpty())
            brawlerlist.addAll(mainBrawlerList)
        brawlerlist.clear()
        brawlerlist.addAll(mainBrawlerList.filter { it.name.startsWith(name, true) })
    }
}
