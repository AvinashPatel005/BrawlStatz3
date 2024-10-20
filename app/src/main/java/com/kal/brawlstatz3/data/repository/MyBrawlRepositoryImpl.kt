package com.kal.brawlstatz3.data.repository

import com.kal.brawlstatz3.data.model.club.Club
import com.kal.brawlstatz3.data.model.event.Event
import com.kal.brawlstatz3.data.model.player.Player
import com.kal.brawlstatz3.util.Response
import com.kal.brawlstatz3.data.remote.MyBrawlApiService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class MyBrawlRepositoryImpl @Inject constructor(
    private val myBrawlApiService: MyBrawlApiService
): MyBrawlRepository {
    override suspend fun getEvent(): Response<Event> {
        return myBrawlApiService.getEvent()
    }

    override suspend fun getProfile(tag: String): Response<Player> {
        return myBrawlApiService.getProfile(tag)
    }

    override suspend fun getClub(tag: String): Response<Club> {
        return myBrawlApiService.getClub(tag)
    }

}