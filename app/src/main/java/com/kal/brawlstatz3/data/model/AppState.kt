package com.kal.brawlstatz3.data.model
import kotlinx.serialization.Serializable

@Serializable
data class AppState(
    val changelog:List<String>,
    val meta: String,
    val link: String,
    val version: String
) {
    constructor() : this(listOf(),"","","1.0")
}