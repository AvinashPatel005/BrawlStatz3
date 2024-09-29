package com.kal.brawlstatz3.data.model.event
import kotlinx.serialization.Serializable
@Serializable
data class Map(
    val dataUpdated: Int,
    val disabled: Boolean,
    val environment: Environment,
    val gameMode: GameMode,
    val hash: String,
    val imageUrl: String,
    val lastActive: Int,
    val link: String,
    val name: String,
    val new: Boolean,
    val stats: List<Stat>,
    val version: Int
)