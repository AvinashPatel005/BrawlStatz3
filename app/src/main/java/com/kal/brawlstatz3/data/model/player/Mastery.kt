package com.kal.brawlstatz3.data.model.player
import kotlinx.serialization.Serializable

@Serializable
data class Mastery(
    val icon: String,
    val name: String,
    val level: String,
    val value: Int
) {
    constructor() : this("", "", "",0)
}