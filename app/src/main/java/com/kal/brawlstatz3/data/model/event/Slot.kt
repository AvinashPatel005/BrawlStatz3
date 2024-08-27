package com.kal.brawlstatz3.data.model.event

data class Slot(
    val background: Any,
    val emoji: String,
    val hash: String,
    val hideForSlot: Int,
    val hideable: Boolean,
    val id: Int,
    val listAlone: Boolean,
    val name: String
)