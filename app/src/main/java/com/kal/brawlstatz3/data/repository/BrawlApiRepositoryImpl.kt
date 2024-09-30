package com.kal.brawlstatz3.data.repository

import com.kal.brawlstatz3.data.model.club.Club
import com.kal.brawlstatz3.data.model.event.Event
import com.kal.brawlstatz3.data.model.player.Player
import com.kal.brawlstatz3.util.Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class BrawlApiRepositoryImpl @Inject constructor(
    private val ktorClient: HttpClient
):BrawlApiRepository {
    override suspend fun getProfile(tag: String): Response<Player> {
        try {
            val result = ktorClient.get("https://zs74ow3jyxjfktc5cti6uzysfu0zjjpa.lambda-url.ap-south-1.on.aws/?tag=lrgjvl9u").body<Player>()
            return Response.Success(result)
        } catch (e: Exception) {
            return Response.Failure(e)
        }
    }

    override suspend fun getClub(tag: String): Response<Club> {
        try {
            val result = ktorClient.get("https://clh6sjyjijbqnxeoxtvxiz2s7a0zncjc.lambda-url.ap-south-1.on.aws/?tag=${tag}").body<Club>()
            return Response.Success(result)
        } catch (e: Exception) {
            return Response.Failure(e)
        }
    }

    override suspend fun getEvents(): Response<Event> {
        try{
            val result = ktorClient.get("https://api.brawlify.com/v1/events").body<Event>()
            return Response.Success(result)
        }
        catch (e: Exception) {
            return Response.Failure(e)
        }
    }


}