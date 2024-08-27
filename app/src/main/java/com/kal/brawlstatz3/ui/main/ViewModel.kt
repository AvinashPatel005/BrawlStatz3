package com.kal.brawlstatz3.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.brawlstatz3.util.Response
import com.kal.brawlstatz3.data.model.Brawler
import com.kal.brawlstatz3.data.model.club.Club
import com.kal.brawlstatz3.data.model.event.Active
import com.kal.brawlstatz3.data.model.player.Player
import com.kal.brawlstatz3.data.repository.BrawlAPI
import com.kal.brawlstatz3.data.repository.BrawlerRepository
import com.kal.brawlstatz3.data.repository.MyBrawlAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val repository: BrawlerRepository,
    private val myBrawlAPI: MyBrawlAPI,
    private val brawlAPI: BrawlAPI
) :ViewModel() {
    var isLoading = mutableStateOf(false)
    var errorLog = mutableStateOf("")

    var brawlerlist = mutableStateListOf<Brawler>()
    var brawlerMap = mapOf<Int,Brawler>()

    var currentMaps = mutableStateListOf<Active>()
    var upcomingMaps = mutableStateListOf<Active>()

    var traits = mutableMapOf<String,Any>()

    var profile = mutableStateOf(Player())
    var club = mutableStateOf(Club())


    init {
        getTraits()
        getBrawlerList()
//        getProfileAndClub()
        getEvents()
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
                    response.data?.let { map ->
                        brawlerMap = map
                        brawlerlist.addAll(map.values.sortedBy { it.name })
                    }
                }
            }
        }
    }
    private fun getProfileAndClub() = viewModelScope.launch {
        val profileRes = myBrawlAPI.getProfile("LRGJVL9U").body()
        if (profileRes != null) {
            profile.value = profileRes
        }

        val clubRes = myBrawlAPI.getClub(profile.value.club.link).body()
        if (clubRes != null) {
            club.value = clubRes
        }
    }
    private fun getEvents() = viewModelScope.launch {
        val res = brawlAPI.getEvent().body()
        if (res != null) {
            filterEvents(upcomingMaps,res.upcoming)
            filterEvents(currentMaps,res.active)
        }
    }

    private fun getTraits() = viewModelScope.launch {
        traits = repository.getTraitList()!!
    }
    private fun filterEvents(list: SnapshotStateList<Active>, from: List<Active>) {
        list.addAll(from.filter { it.slot.name.contains("Daily") })
        list.addAll(from.filter { it.slot.name.contains("Weekly") })
        list.removeAll(from.filter { it.map.hash.contains("-Duo") }.toSet())
    }
}