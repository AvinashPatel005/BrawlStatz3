package com.kal.brawlstatz3.data.model.player

import kotlinx.serialization.Serializable

@Serializable
data class BrawlersData(
    val currentTrophy: String,
    val dp: String,
    val gadget: List<String>,
    val gears: List<String>,
    val highestTrophy: String,
    val mastery:Template?=null,
    val name: String,
    val powerLevel: String,
    val seasonEndTrophy: String,
    val starPower: List<String>,
    val tier: String
)