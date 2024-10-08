package com.kal.brawlstatz3.data.model.player

import kotlinx.serialization.Serializable

@Serializable
data class BattleLog(
    val icon: String,
    val mode: String,
    val result: Int,
    val time: String,
    val trophy: String
)