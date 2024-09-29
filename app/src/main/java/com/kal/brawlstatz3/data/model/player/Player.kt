package com.kal.brawlstatz3.data.model.player

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val accountCreated: String,
    val battleCard: List<String>,
    val battleLog: List<BattleLog>,
    val brawlersData: List<BrawlersData>,
    val challenges: String,
    val club: Club,
    val dp: String,
    val fame: Template,
    val favourite: String,
    val isMasteryDataAvailable: Boolean,
    val name: String,
    val playerTag: String,
    val progress: List<Template>,
    val ranked: Ranked,
    val stats: List<Template>,
    val trophyArray: List<Int>,
    val winStreak: Template
){
    constructor():this("", listOf(), listOf(), listOf(),"",Club(),"",Template(),"",false,"","", listOf(),Ranked(),
        listOf(),
        listOf(),Template())
}