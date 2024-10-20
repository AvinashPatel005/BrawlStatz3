package com.kal.brawlstatz3.data.repository

import com.kal.brawlstatz3.data.model.brawler.Brawler
import com.kal.brawlstatz3.data.remote.BrawlerService
import com.kal.brawlstatz3.util.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BrawlerRepositoryImpl @Inject constructor(
    private val brawlerService: BrawlerService,
): BrawlerRepository {
    override fun getBrawlerList(): Flow<Response<Map<Int, Brawler>>> {
        return brawlerService.getBrawlerList()
    }

    override suspend fun getTraitList(): Response<MutableMap<String, Any>?> {
        return brawlerService.getTraitList()
    }
}