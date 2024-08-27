package com.kal.brawlstatz3.data.model.event

data class Active(
    val endTime: String,
    val map: Map,
    val modifier: Any,
    val predicted: Boolean,
    val reward: Int,
    val slot: Slot,
    val startTime: String
)