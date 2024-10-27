package com.kal.brawlstatz3.data.model.player

import kotlinx.serialization.Serializable

@Serializable
data class Ranked(
    val current: Template,
    val highest: Template
) {
    constructor() : this(Template(), Template())
}