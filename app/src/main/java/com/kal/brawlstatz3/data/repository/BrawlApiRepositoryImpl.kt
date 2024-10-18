package com.kal.brawlstatz3.data.repository

import javax.inject.Inject

class BrawlApiRepositoryImpl @Inject constructor(
    val client: BrawlApiRepository
){
    suspend fun getData(){
        client.getProfile("lrgjvl9u")
    }
}