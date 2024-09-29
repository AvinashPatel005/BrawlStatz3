package com.kal.brawlstatz3.data.model.player

import kotlinx.serialization.Serializable

@Serializable
data class Club(
    val link: String,
    val name: String
){
    constructor():this("","")
}