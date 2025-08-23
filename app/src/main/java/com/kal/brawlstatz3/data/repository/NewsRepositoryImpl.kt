package com.kal.brawlstatz3.data.repository

import com.kal.brawlstatz3.data.model.News
import com.kal.brawlstatz3.data.remote.BrawlerService
import com.kal.brawlstatz3.data.remote.NewsService
import com.kal.brawlstatz3.util.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsService: NewsService,
): NewsRepository {
    override suspend fun getNewsList(): Response<List<News>> {
        return newsService.getNewsList()
    }
}