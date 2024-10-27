package com.kal.brawlstatz3.data.model.brawler

data class NameDescription(
    val description: String,
    val name: String
) {
    constructor() : this("", "")
}