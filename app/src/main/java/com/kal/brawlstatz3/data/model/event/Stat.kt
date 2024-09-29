package com.kal.brawlstatz3.data.model.event
import kotlinx.serialization.Serializable
@Serializable
data class Stat(
    val brawler: Int,
    val useRate: Double,
    val winRate: Double
)