package com.kal.brawlstatz3.data.model.player

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val accountCreated: String?=null,
    val battleCard: List<String>?=null,
    val battleLog: List<BattleLog>,
    val brawlersData: List<BrawlersData>,
    val challenges: String?=null,
    val club: Club,
    val dp: String,
    val fame: Template?=null,
    val favourite: String?=null,
    val isMasteryDataAvailable: Boolean,
    val name: String,
    val playerTag: String,
    val progress: List<Template>,
    val ranked: Ranked?=null,
    val stats: List<Template>,
    val trophyArray: List<Int>,
    val winStreak: Template?=null
){
    constructor():this("", listOf(), listOf(), listOf(),"",Club(),"",Template(),"",false,"","", listOf(),Ranked(),
        listOf(),
        listOf(),Template())
}