package com.kal.brawlstatz3.feature.profile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.kal.brawlstatz3.data.model.player.BattleLog

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BattleCard(battle:BattleLog) {
    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.height(30.dp).padding(2.dp).background(if(battle.result>0)Color(0xff4e8752) else if(battle.result==0) Color(0xff4cb3e0) else Color(0xffed9aa2),shape= RoundedCornerShape(4.dp)).padding(2.dp)){
        GlideImage(model = battle.icon, contentDescription = null, modifier = Modifier.weight(1f))
        GlideImage(model = battle.brawler, contentDescription = null, modifier = Modifier.weight(1f))
    }
}