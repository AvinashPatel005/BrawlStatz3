package com.kal.brawlstatz3.data.model.club

data class Club(
    val description: String,
    val dp: String,
    val members: List<Member>,
    val name: String,
    val president: String,
    val status: String,
    val totalTrophy: Int
){
    constructor():this("","", listOf(),"","","",0)
}