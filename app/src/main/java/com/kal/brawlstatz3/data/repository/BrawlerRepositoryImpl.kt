package com.kal.brawlstatz3.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.toObject
import com.kal.brawlstatz3.util.Response
import com.kal.brawlstatz3.data.model.brawler.Brawler
import com.kal.brawlstatz3.data.remote.BrawlerService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

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