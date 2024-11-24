package com.kal.brawlstatz3.util

import androidx.compose.ui.graphics.Color
import com.kal.brawlstatz3.data.model.brawler.Brawler

data class Tier(val name: String, val color: Color, val brawlers: ArrayList<Brawler>) {
    constructor() : this("N", Color.Black, ArrayList())
}