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
import com.kal.brawlstatz3.data.model.event.Event
import com.kal.brawlstatz3.data.model.player.Player
import com.kal.brawlstatz3.data.repository.BrawlApiRepository
import com.kal.brawlstatz3.data.repository.BrawlerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val brawlerRepository: BrawlerRepository,
    private val brawlApiRepository: BrawlApiRepository
) :ViewModel() {
    var isLoading = mutableStateOf(false)
    var errorLog = mutableStateOf("")


    var brawlerList = mutableStateListOf<Brawler>()
    var brawlerMap = mapOf<Int,Brawler>()

    var currentMaps = mutableStateListOf<Active>()
    var upcomingMaps = mutableStateListOf<Active>()

    var traits = mutableMapOf<String,Any>()

    var profile = mutableStateOf(Player())
    var club = mutableStateOf(Club())


    init {
        getTraits()
        getBrawlerList()
        getEvents()
        getProfileAndClub()
    }

    private fun getBrawlerList() = viewModelScope.launch {
        brawlerRepository.getBrawlerList().collect{ response ->
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
                    brawlerList.clear()
                    response.data.let { map ->
                        brawlerMap = map
                        brawlerList.addAll(map.values.sortedBy { it.name })
                    }
                }
            }
        }
    }

    private fun getTraits() = viewModelScope.launch {
        brawlerRepository.getTraitList().let {response->
            when(response){
                is Response.Success->{
                    traits = response.data!!
                }
                is Response.Failure -> {}
                is Response.Loading -> {}
            }
        }
    }

    private fun getProfileAndClub() = viewModelScope.launch {
        brawlApiRepository.getProfile("lrgjvl9u").let {response->
            when(response){
                is Response.Success ->{
                    profile.value = response.data
                    getClub()
                }
                is Response.Failure -> {}
                is Response.Loading -> {}
            }
        }
    }

    private fun getClub() = viewModelScope.launch {
        brawlApiRepository.getClub(profile.value.club.tag).let {response->
            when(response){
                is Response.Success ->{
                    club.value=response.data
                }
                is Response.Failure -> {}
                is Response.Loading -> {}
            }
        }
    }

    private fun getEvents() = viewModelScope.launch {
        brawlApiRepository.getEvents().let {
            when(it){
                is Response.Success ->{
                    filterEvents(upcomingMaps,it.data.upcoming)
                    filterEvents(currentMaps,it.data.active)
                }
                is Response.Failure -> {}
                is Response.Loading -> {}
            }
        }
    }


    private fun filterEvents(list: SnapshotStateList<Active>, from: List<Active>) {
        list.addAll(from.filter { it.slot.name.contains("Daily") })
        list.addAll(from.filter { it.slot.name.contains("Weekly") })
        list.removeAll(from.filter { it.map.hash.contains("-Duo") }.toSet())
    }
}