package com.kal.brawlstatz3.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.toObject
import com.kal.brawlstatz3.data.model.Brawler
import com.kal.brawlstatz3.util.UiState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import javax.inject.Inject

class BrawlerRepositoryImpl @Inject constructor(
    private val brawlerReference: CollectionReference,
):BrawlerRepository {
    @OptIn(DelicateCoroutinesApi::class)
    override fun getBrawlerList(uiState: Channel<UiState>) {
        GlobalScope.launch {
            uiState.send(UiState.Loading)
        }
        brawlerReference.orderBy("name").addSnapshotListener { value, e ->
            if (e != null) {
                GlobalScope.launch {
                    uiState.send(UiState.Error(e.message.toString()))
                }
                return@addSnapshotListener
            }

            val brawlers = ArrayList<Brawler>()
            val brawlersMap = hashMapOf<Int,Brawler>()
            for (doc in value!!) {
                val brawler = doc.toObject<Brawler>()
                brawlersMap.put(brawler.id,brawler)
                brawlers.add(brawler)
            }

            GlobalScope.launch {
                uiState.send(UiState.Success(brawlers,brawlersMap))
            }

        }

    }
}