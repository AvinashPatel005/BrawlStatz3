package com.kal.brawlstatz3.data.model.player

data class Player(
    val accountCreated: String,
    val battleCard: List<String>,
    val battleLog: List<BattleLog>,
    val brawlersData: List<BrawlersData>,
    val challenges: String,
    val club: Club,
    val dp: String,
    val fame: Fame,
    val favourite: String,
    val isMasteryDataAvailable: Boolean,
    val name: String,
    val playerTag: String,
    val progress: List<Progress>,
    val ranked: Ranked,
    val stats: List<Stat>,
    val trophyArray: List<Int>,
    val winStreak: WinStreak
)