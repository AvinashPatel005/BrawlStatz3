package com.kal.brawlstatz3.data.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.toObject
import com.kal.brawlstatz3.data.model.brawler.Brawler
import com.kal.brawlstatz3.util.Response
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class BrawlerService @Inject constructor(
    @Named("Brawlers") private val brawlerReference: CollectionReference,
    @Named("Others") private val othersReference: CollectionReference
) {
    fun getBrawlerList() = callbackFlow {
        trySend(Response.Loading)
        var snapshotListener = brawlerReference.orderBy("name").addSnapshotListener { value, e ->
            val brawlers = ArrayList<Brawler>()
            val brawlersMap = hashMapOf<Int, Brawler>()
            val response = if (value != null) {
                for (doc in value) {
                    val brawler = doc.toObject<Brawler>()
                    brawlersMap[brawler.id] = brawler
                    brawlers.add(brawler)
                }
                Response.Success(brawlersMap)
            } else {
                Response.Failure(e)
            }
            trySend(response)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }


    suspend fun getTraitList(): Response<MutableMap<String, Any>?> {
        try {
            val res = othersReference.document("traits").get().await()
            return Response.Success(res.data)
        } catch (e: Exception) {
            return Response.Failure(e = e)
        }
    }
}