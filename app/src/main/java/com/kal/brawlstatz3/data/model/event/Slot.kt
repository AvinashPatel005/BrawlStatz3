package com.kal.brawlstatz3.data.model.event

import kotlinx.serialization.Serializable

@Serializable
data class Slot(
    val emoji: String,
    val hash: String,
    val hideable: Boolean,
    val listAlone: Boolean,
    val name: String
)