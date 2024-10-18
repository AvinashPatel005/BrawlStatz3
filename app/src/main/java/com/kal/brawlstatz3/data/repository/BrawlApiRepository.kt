package com.kal.brawlstatz3.data.repository

import com.kal.brawlstatz3.data.model.club.Club
import com.kal.brawlstatz3.data.model.event.Event
import com.kal.brawlstatz3.data.model.player.Player
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BrawlApiRepository {
    @GET("https://zs74ow3jyxjfktc5cti6uzysfu0zjjpa.lambda-url.ap-south-1.on.aws/")
    suspend fun getProfile(@Query("tag") tag:String):Response<Player>

    @GET("https://clh6sjyjijbqnxeoxtvxiz2s7a0zncjc.lambda-url.ap-south-1.on.aws/")
    suspend fun getClub(@Query("tag") tag:String):Response<Club>

    suspend fun getEvents():Response<Event>
}