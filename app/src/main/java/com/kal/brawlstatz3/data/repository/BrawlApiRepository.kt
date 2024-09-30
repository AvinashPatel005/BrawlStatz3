package com.kal.brawlstatz3.data.repository

import com.kal.brawlstatz3.data.model.club.Club
import com.kal.brawlstatz3.data.model.event.Event
import com.kal.brawlstatz3.data.model.player.Player
import com.kal.brawlstatz3.util.Response

interface BrawlApiRepository {
    suspend fun getProfile(tag:String):Response<Player>
    suspend fun getClub(tag:String):Response<Club>
    suspend fun getEvents():Response<Event>
}