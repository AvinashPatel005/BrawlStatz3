package com.kal.brawlstatz3.data.model.player

import kotlinx.serialization.Serializable

@Serializable
data class Template(
    val icon: String,
    val name: String,
    val value: String
){
    constructor():this("","","")
}