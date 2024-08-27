package com.kal.brawlstatz3.ui.brawlers.components

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kal.brawlstatz3.R

data class AdvanceImageModel(
    val offsetX: Dp,
    val rotation: Float,
    val placeholder: Int,
    val alignment: Alignment
)
val counterAdvanceImageList = listOf(
    AdvanceImageModel(
        offsetX = 0.dp,
        rotation = 10f,
        placeholder = R.drawable.placeholder2,
        alignment = Alignment.TopEnd
    ),
    AdvanceImageModel(
        offsetX = 0.dp,
        rotation = 10f,
        placeholder = R.drawable.placeholder3,
        alignment = Alignment.BottomEnd
    ),
    AdvanceImageModel(
        offsetX = 20.dp,
        rotation = -10f,
        placeholder = R.drawable.placeholder4,
        alignment = Alignment.CenterEnd
    )
)




