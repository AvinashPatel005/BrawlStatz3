package com.kal.brawlstatz3.data.remote

import com.kal.brawlstatz3.util.Response
import com.kal.brawlstatz3.data.model.club.Club
import com.kal.brawlstatz3.data.model.event.Event
import com.kal.brawlstatz3.data.model.player.Player
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class MyBrawlApiService @Inject constructor(private val ktorClient: HttpClient) {
    suspend fun getEvent(): Response<Event> {
        try {
            val response = ktorClient.get("https://api.brawlify.com/v1/events").body<Event>()
            return Response.Success(response)
        } catch (e: Exception) {
            return Response.Failure(e = e)
        }
    }

    suspend fun getProfile(tag: String): Response<Player> {
        try {
            val response = ktorClient.get("https://zs74ow3jyxjfktc5cti6uzysfu0zjjpa.lambda-url.ap-south-1.on.aws/?tag=${tag}").body<Player>()
            return Response.Success(response)
        } catch (e: Exception) {
            return Response.Failure(e = e)
        }
    }

    suspend fun getClub(tag: String): Response<Club> {
        try {
            val response = ktorClient.get("https://clh6sjyjijbqnxeoxtvxiz2s7a0zncjc.lambda-url.ap-south-1.on.aws/?tag=${tag}").body<Club>()
            return Response.Success(response)
        } catch (e: Exception) {
            return Response.Failure(e = e)
        }
    }
}