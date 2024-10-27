package com.kal.brawlstatz3.feature.events.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.brawlstatz3.data.model.event.Active
import com.kal.brawlstatz3.data.repository.MyBrawlRepository
import com.kal.brawlstatz3.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val myBrawlRepository: MyBrawlRepository
) : ViewModel() {
    var isLoading = mutableStateOf(false)
    var errorLog = mutableStateOf("")

    var currentMaps = mutableStateListOf<Active>()
    var upcomingMaps = mutableStateListOf<Active>()

    init {
        getEvents()
    }

    private fun getEvents() = viewModelScope.launch {
        isLoading.value = true
        println("Fetching Events")
        myBrawlRepository.getEvent().let { response ->
            when (response) {
                is Response.Failure -> {
                    errorLog.value = response.e?.message.toString()
                    isLoading.value = false
                }

                is Response.Loading -> {
                    isLoading.value = true
                }

                is Response.Success -> {
                    currentMaps.addAll(response.data.active)
                    upcomingMaps.addAll(response.data.upcoming)
                    isLoading.value = false
                }
            }
        }
    }
}
