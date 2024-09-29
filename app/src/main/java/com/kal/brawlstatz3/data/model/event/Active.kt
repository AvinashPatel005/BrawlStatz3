package com.kal.brawlstatz3.data.model.event
import kotlinx.serialization.Serializable
@Serializable
data class Active(
    val endTime: String,
    val map: Map,
    val modifier: String?,
    val predicted: Boolean,
    val reward: Int,
    val slot: Slot,
    val startTime: String
)