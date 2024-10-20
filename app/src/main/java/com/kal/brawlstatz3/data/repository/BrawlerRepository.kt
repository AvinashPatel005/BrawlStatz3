package com.kal.brawlstatz3.data.repository

import com.kal.brawlstatz3.data.model.brawler.Brawler
import com.kal.brawlstatz3.util.Response
import kotlinx.coroutines.flow.Flow

interface BrawlerRepository {
    fun getBrawlerList(): Flow<Response<Map<Int, Brawler>>>
    suspend fun getTraitList(): Response<MutableMap<String, Any>?>
}