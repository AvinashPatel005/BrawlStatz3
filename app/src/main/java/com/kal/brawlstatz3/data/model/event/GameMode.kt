package com.kal.brawlstatz3.data.model.event

import kotlinx.serialization.Serializable

@Serializable
data class GameMode(
    val bgColor: String,
    val color: String,
    val hash: String,
    val imageUrl: String,
    val name: String,
    val version: Int
)