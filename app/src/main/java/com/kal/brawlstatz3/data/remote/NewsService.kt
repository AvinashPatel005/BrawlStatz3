package com.kal.brawlstatz3.data.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.kal.brawlstatz3.data.model.News
import com.kal.brawlstatz3.util.Response
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class NewsService @Inject constructor(
    @Named("News") private val newsReference: CollectionReference
) {
    suspend fun getNewsList(): Response<List<News>> {
        return try {
            val snapshot = newsReference.orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()
            val newsList = snapshot.toObjects(News::class.java)
            Response.Success(newsList)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}