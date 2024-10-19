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
import com.kal.brawlstatz3.data.repository.BrawlerRepository
import com.kal.brawlstatz3.data.repository.MyBrawlRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val repository: BrawlerRepository,
    private val myBrawlRepository: MyBrawlRepository
) :ViewModel() {
    var isLoading = mutableStateOf(false)
    var errorLog = mutableStateOf("")

    var brawlerlist = mutableStateListOf<Brawler>()
    var brawlerMap = mapOf<Int,Brawler>()

    var currentMaps = mutableStateListOf<Active>()
    var upcomingMaps = mutableStateListOf<Active>()

    var traits = mutableMapOf<String,Any>()

    var profile = mutableStateOf(Player().copy(accountCreated = "Fetching"))
    var club = mutableStateOf(Club())


    init {
        getTraits()
        getBrawlerList()
        getEvents()
        viewModelScope.launch {
            getProfile("lrgjvl9u")
            getClub(profile.value.club.tag)
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

    private fun getEvents() = viewModelScope.launch{
        println("Fetching Event")
        myBrawlRepository.getEvent().let { response->
            when(response){
                is Response.Failure -> {
                    errorLog.value = response.e?.message.toString()
                    println(response.e.toString())
                }
                is Response.Loading -> {

                }
                is Response.Success -> {
                    println(response.data)
                    currentMaps.addAll(response.data.active)
                    upcomingMaps.addAll(response.data.upcoming)
                }
            }
        }
    }

    private suspend fun getProfile(tag:String) {
        println("Fetching Profile")
        myBrawlRepository.getProfile(tag).let { response ->
            when(response){
                is Response.Failure -> {
                    errorLog.value = response.e?.message.toString()
                    profile.value = Player().copy(accountCreated =errorLog.value)
                }

                is Response.Loading -> {

                }
                is Response.Success -> {
                    profile.value = response.data
                }
            }
        }
    }

    private suspend fun getClub(tag:String){
        println("Fetching Club" + profile.value.club.tag)
        myBrawlRepository.getClub(tag).let { response ->
            when(response){
                is Response.Failure -> {
                    errorLog.value = response.e?.message.toString()
                }

                is Response.Loading -> {

                }
                is Response.Success -> {
                    club.value = response.data
                }
            }
        }
    }
}