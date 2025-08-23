package com.kal.brawlstatz3.feature.updates.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.brawlstatz3.data.model.News
import com.kal.brawlstatz3.data.model.event.Active
import com.kal.brawlstatz3.data.repository.BrawlerRepository
import com.kal.brawlstatz3.data.repository.NewsRepository
import com.kal.brawlstatz3.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    var isLoading = mutableStateOf(false)
    var errorLog = mutableStateOf("")

    var newsList = mutableStateListOf<News>()

    init {
        getEvents()
    }

    private fun getEvents() = viewModelScope.launch {
        isLoading.value = true
        println("Newsdata")
        repository.getNewsList().let { response ->
            when (response) {
                is Response.Failure -> {
                    println("Newsdata "+response)
                    errorLog.value = response.e?.message.toString()
                    isLoading.value = false
                }

                is Response.Loading -> {
                    isLoading.value = true
                }

                is Response.Success -> {
                    println("Newsdata "+response)
                    newsList.addAll(response.data)
                    isLoading.value = false
                }
            }
        }
    }
}