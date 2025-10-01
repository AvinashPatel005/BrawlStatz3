package com.kal.brawlstatz3.feature.profile.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.brawlstatz3.data.model.club.Club
import com.kal.brawlstatz3.data.model.player.BrawlersData
import com.kal.brawlstatz3.data.model.player.Player
import com.kal.brawlstatz3.data.repository.MyBrawlRepository
import com.kal.brawlstatz3.feature.profile.ProfileUiEvent
import com.kal.brawlstatz3.util.Response
import com.kal.brawlstatz3.util.getStringFromPrefs
import com.kal.brawlstatz3.util.getStringListFromPrefs
import com.kal.brawlstatz3.util.saveStringListToPrefs
import com.kal.brawlstatz3.util.saveStringToPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val myBrawlRepository: MyBrawlRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var isLoading = mutableStateOf(false)
    var errorLog = mutableStateOf("")

    var profile = mutableStateOf(Player())
    var club = mutableStateOf(Club())
    var brawlers = mutableStateListOf<BrawlersData>()
    var sortBy = mutableIntStateOf(-1)

    var isMenu = mutableStateOf(false)
    var isAdding = mutableStateOf(false)
    var isDeleting = mutableStateOf(false)
    var inputTag = mutableStateOf("")
    var initialInputTag = mutableStateOf("")
    var isRefreshing = mutableStateOf(false)

    var tagList = mutableStateListOf<String>()
    var currentTag = mutableStateOf("")

    private var prevTag = ""

    init{
        loadTagsAndFetchData()
    }
    private fun loadTagsAndFetchData() {
        viewModelScope.launch {
            val tags = getStringListFromPrefs(context,"tag_list")
            val current = getStringFromPrefs(context,"tag_index")

            tagList.clear()
            tagList.addAll(tags)
            currentTag.value = if(current!=null && current!="") current else if (tagList.size>0) tagList[0] else ""

            if (current != null && current != "") {
                getProfile(current)
            }
        }
    }

    private suspend fun getProfile(tag: String) {
        isLoading.value = true
        errorLog.value = ""
        myBrawlRepository.getProfile(tag).let { response ->
            when (response) {
                is Response.Failure -> {
                    errorLog.value = response.e?.message.toString()
                    isLoading.value = true
                }

                is Response.Loading -> {
                    isLoading.value = true
                }

                is Response.Success -> {
                    profile.value = response.data
                    brawlers.addAll(response.data.brawlersData)
                    if(profile.value.club.tag!="-1"){
                        getClub(profile.value.club.tag)
                    }
                    else{
                        isLoading.value = false
                    }
                }
            }
        }
    }

    private suspend fun getClub(tag: String) {
        myBrawlRepository.getClub(tag).let { response ->
            when (response) {
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

    fun onEvent(event:ProfileUiEvent){
        when(event){
            is ProfileUiEvent.ItemAdd -> {
                if (inputTag.value.isNotEmpty() && !tagList.contains(inputTag.value)){
                    if (tagList.size==0){
                        isLoading.value=true
                    }
                    tagList.add(inputTag.value)

                    inputTag.value=""
                    isAdding.value=false
                    saveStringListToPrefs(context,"tag_list",tagList.toList())
                }
                else if(initialInputTag.value.isNotEmpty()){
                    tagList.clear()
                    tagList.add(initialInputTag.value)
                    currentTag.value = initialInputTag.value
                    initialInputTag.value=""
                    saveStringToPrefs(context,"tag_index",currentTag.value)
                    saveStringListToPrefs(context,"tag_list",tagList.toList())

                    viewModelScope.launch {
                        getProfile(currentTag.value)
                    }

                }
            }
            is ProfileUiEvent.InitialTagValue ->{
                initialInputTag.value = event.tag
            }
            is ProfileUiEvent.ItemDelete -> {
                tagList.remove(event.tag)
                saveStringListToPrefs(context,"tag_list",tagList.toList())
                if (currentTag.value==event.tag){
                    currentTag.value = if(tagList.size==0) "" else tagList[0]
                    saveStringToPrefs(context,"tag_index",currentTag.value)
                    profile.value=Player()
                    club.value=Club()
                }
            }

            is ProfileUiEvent.Reload ->{
                viewModelScope.launch {
                    getProfile(currentTag.value)
                }
            }

            is ProfileUiEvent.SelectTag->{
                currentTag.value = event.tag
                saveStringToPrefs(context,"tag_index",event.tag)
            }
            is ProfileUiEvent.Refresh -> {
                isRefreshing.value = true
                viewModelScope.launch {
                    getProfile(currentTag.value)
                    isRefreshing.value = false
                }
            }
            is ProfileUiEvent.AddClicked -> {
                inputTag.value = ""
                isDeleting.value = false
                isAdding.value = !isAdding.value
            }
            is ProfileUiEvent.DeleteClicked -> {
                isAdding.value = false
                isDeleting.value = !isDeleting.value
            }
            is ProfileUiEvent.InputTagValueChanged -> {
                inputTag.value = event.tag
            }
            is ProfileUiEvent.MenuDismiss -> {
                isMenu.value = false
                isAdding.value = false
                isDeleting.value = false
                inputTag.value = ""
                if(prevTag!=currentTag.value){
                    viewModelScope.launch {
                        getProfile(currentTag.value)
                    }
                }
            }
            ProfileUiEvent.MenuStart -> {
                isMenu.value = true
                prevTag = currentTag.value
            }
        }
    }


    fun sortBrawlers(sortBy:Int) {
        val brawlersData = profile.value.brawlersData
        brawlers.clear()
        when (sortBy) {
            0 -> brawlers.addAll(brawlersData.sortedBy { it.name })
            1 -> brawlers.addAll(brawlersData.sortedByDescending { it.currentTrophy })
            2 -> brawlers.addAll(brawlersData.sortedByDescending { it.highestTrophy })
            3 -> brawlers.addAll(brawlersData.sortedByDescending { it.powerLevel })
            4 -> brawlers.addAll(brawlersData.sortedByDescending { it.tierValue })
            5 -> brawlers.addAll(brawlersData.sortedByDescending { it.mastery?.value })
            else -> brawlers.addAll(brawlersData)
        }

    }


    fun cycleSort(){
        if (sortBy.intValue < 5) sortBy.intValue += 1
        else sortBy.intValue = 0
        sortBrawlers(sortBy.intValue)
    }

}
