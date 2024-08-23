package com.kal.brawlstatz3.data.model

data class Brawler(
    val about: String,
    val attack: NameDescription,
    val attackSuper: NameDescription,
    val bestBuild: BestBuild,
    val counters: List<Int>,
    val gadgets: List<NameDescription>,
    val id: Int,
    val mastery: String,
    val model3d: String,
    val movementSpeed: String,
    val name: String,
    val rarity: String,
    val starpowers: List<NameDescription>,
    val tier: String,
    val trait: String?=null,
    val type: String,
    val version: Int
) {
    constructor(): this("",NameDescription(),NameDescription(),BestBuild(), listOf(), listOf(),0,"","","","","",
        listOf(),"","","",0)
}