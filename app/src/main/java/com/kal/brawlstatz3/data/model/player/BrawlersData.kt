package com.kal.brawlstatz3.data.model.player

import kotlinx.serialization.Serializable

@Serializable
data class BrawlersData(
    val currentTrophy: Int,
    val dp: String,
    val gadget: List<String>,
    val gears: List<String>,
    val highestTrophy: Int,
    val mastery: Mastery? = null,
    val name: String,
    val powerLevel: Int,
    val seasonEndTrophy: Int,
    val starPower: List<String>,
    val tier: String,
    val tierValue : Int
)