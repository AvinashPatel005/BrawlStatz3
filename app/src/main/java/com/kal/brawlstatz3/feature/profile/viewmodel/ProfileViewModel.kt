package com.kal.brawlstatz3.feature.profile.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.brawlstatz3.data.model.club.Club
import com.kal.brawlstatz3.data.model.player.Player
import com.kal.brawlstatz3.data.repository.MyBrawlRepository
import com.kal.brawlstatz3.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val myBrawlRepository: MyBrawlRepository
) :ViewModel() {
    var isLoading = mutableStateOf(false)
    var errorLog = mutableStateOf("")

    var profile = mutableStateOf(Player().copy(accountCreated = "Fetching"))
    var club = mutableStateOf(Club())

    init {
        viewModelScope.launch {
            getProfile("lrgjvl9u")
            getClub(profile.value.club.tag)
        }
    }

    private suspend fun getProfile(tag:String) {
        isLoading.value=true
        println("Fetching Profile")
        myBrawlRepository.getProfile(tag).let { response ->
            when(response){
                is Response.Failure -> {
                    errorLog.value = response.e?.message.toString()
                    profile.value = Player().copy(accountCreated =errorLog.value)
                    isLoading.value = true
                }

                is Response.Loading -> {
                    isLoading.value = true
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
                    isLoading.value = false
                }

                is Response.Loading -> {
                    isLoading.value = true
                }
                is Response.Success -> {
                    club.value = response.data
                    isLoading.value = false
                }
            }
        }
    }
}
