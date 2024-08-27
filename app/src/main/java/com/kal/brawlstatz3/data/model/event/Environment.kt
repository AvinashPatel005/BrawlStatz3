package com.kal.brawlstatz3.data.model.event

data class Environment(
    val hash: String,
    val id: Int,
    val imageUrl: String,
    val name: String,
    val path: String,
    val version: Int
)