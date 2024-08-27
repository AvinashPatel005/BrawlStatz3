package com.kal.brawlstatz3.data.repository

import com.kal.brawlstatz3.data.model.club.Club
import com.kal.brawlstatz3.data.model.player.Player
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface MyBrawlAPI {
    @Headers("x-api-key:dp23wCISpL5lWHzSkjx7taGKBMFOTGMN3eSAc011")
    @GET("profile")
    suspend fun getProfile(
        @Query("tag") tag:String
    ):Response<Player>

    @Headers("x-api-key:dp23wCISpL5lWHzSkjx7taGKBMFOTGMN3eSAc011")
    @GET("club")
    suspend fun getClub(
        @Query("tag") tag:String
    ):Response<Club>
}