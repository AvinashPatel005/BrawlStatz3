package com.kal.brawlstatz3.ui.brawlers

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kal.brawlstatz3.data.model.ExpandableCardModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class BrawlersViewModel @Inject constructor() :ViewModel(
) {
    val expandedCard = mutableStateOf(ExpandableCardModel())
}