package com.kal.brawlstatz3.data.repository

import com.kal.brawlstatz3.data.model.AppState
import com.kal.brawlstatz3.data.model.News
import com.kal.brawlstatz3.data.model.brawler.Brawler
import com.kal.brawlstatz3.util.Response
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsList(): Response<List<News>>
}