package com.kal.brawlstatz3.data.model.brawler

data class BestBuild(
    val gadget: Int,
    val gears: List<String>,
    val starpower: Int
){
    constructor():this(-1, listOf(),-1)
}