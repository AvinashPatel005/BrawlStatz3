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
    private val ktorClient: HttpClient
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

        viewModelScope.launch {
            getEvents()
        }
        viewModelScope.launch {
            getProfile()
            getClub()
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
                    response.data?.let { map ->
                        brawlerMap = map
                        brawlerlist.addAll(map.values.sortedBy { it.name })
                    }
                }
            }
        }
    }
    suspend fun getProfile() {

        try {
            profile.value = ktorClient.get("https://zs74ow3jyxjfktc5cti6uzysfu0zjjpa.lambda-url.ap-south-1.on.aws/?tag=lrgjvl9u").body<Player>()
        } catch (e: Exception) {
            // handle exception
        }
    }
    suspend fun getClub() {
        try {
            club.value = ktorClient.get("https://clh6sjyjijbqnxeoxtvxiz2s7a0zncjc.lambda-url.ap-south-1.on.aws/?tag=${profile.value.club.link}").body<Club>()
        } catch (e: Exception) {
            // handle exception
        }
    }


    suspend fun getEvents() = viewModelScope.launch {
        val res:Event = ktorClient.get("https://api.brawlify.com/v1/events").body<Event>()
        filterEvents(upcomingMaps,res.upcoming)
        filterEvents(currentMaps,res.active)
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