package com.kal.brawlstatz3.data.model.event
import kotlinx.serialization.Serializable
@Serializable
data class Environment(
    val hash: String,
    val id: Int,
    val imageUrl: String,
    val name: String,
    val path: String,
    val version: Int
)