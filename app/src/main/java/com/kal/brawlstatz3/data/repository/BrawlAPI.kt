package com.kal.brawlstatz3.data.repository

import com.kal.brawlstatz3.data.model.event.Event
import retrofit2.Response
import retrofit2.http.GET

interface BrawlAPI {
    @GET("events")
    suspend fun getEvent() : Response<Event>
}