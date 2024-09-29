package com.kal.brawlstatz3.data.model.club

import kotlinx.serialization.Serializable

@Serializable
data class Member(
    val dp: String,
    val name: String,
    val role: String,
    val tag: String,
    val trophy: String
)