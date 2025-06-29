package com.kal.brawlstatz3.util

import androidx.compose.ui.graphics.Color
import com.kal.brawlstatz3.ui.theme.epic
import com.kal.brawlstatz3.ui.theme.legendary
import com.kal.brawlstatz3.ui.theme.mythic
import com.kal.brawlstatz3.ui.theme.rare
import com.kal.brawlstatz3.ui.theme.starting
import com.kal.brawlstatz3.ui.theme.super_rare
import com.kal.brawlstatz3.ui.theme.ultra_legendary

fun getRarityColor(rarity: String): Color {
    return when (rarity) {
        "ULTRA LEGENDARY" -> ultra_legendary
        "LEGENDARY" -> legendary
        "MYTHIC" -> mythic
        "EPIC" -> epic
        "SUPER RARE" -> super_rare
        "RARE" -> rare
        "STARTING" -> starting
        else -> {
            Color.Black
        }
    }
}