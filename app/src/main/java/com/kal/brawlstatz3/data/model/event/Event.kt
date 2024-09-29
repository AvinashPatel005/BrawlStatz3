package com.kal.brawlstatz3.data.model.event

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val active: List<Active>,
    val upcoming: List<Active>
)