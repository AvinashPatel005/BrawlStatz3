package com.kal.brawlstatz3.data.repository

import com.kal.brawlstatz3.util.Response
import com.kal.brawlstatz3.data.model.Brawler
import kotlinx.coroutines.flow.Flow

interface BrawlerRepository {
    fun getBrawlerList(): Flow<Response<Map<Int,Brawler>>>
    suspend fun getTraitList():MutableMap<String, Any>?
}