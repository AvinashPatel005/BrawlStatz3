package com.kal.brawlstatz3.util

import androidx.compose.ui.graphics.Color
import com.kal.brawlstatz3.ui.theme.epic
import com.kal.brawlstatz3.ui.theme.legendary
import com.kal.brawlstatz3.ui.theme.mythic
import com.kal.brawlstatz3.ui.theme.rare
import com.kal.brawlstatz3.ui.theme.starting
import com.kal.brawlstatz3.ui.theme.superrare

fun getRarityColor(rarity:String):Color{
    return when(rarity){
        "LEGENDARY" -> legendary
        "MYTHIC" ->mythic
        "EPIC" ->epic
        "SUPER RARE" -> superrare
        "RARE" ->rare
        "STARTING" ->starting
        else -> {Color.Black}
    }
}