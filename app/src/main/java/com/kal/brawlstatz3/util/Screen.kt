package com.kal.brawlstatz3.util

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Brawlers : Screen()

    @Serializable
    data object Meta : Screen()

    @Serializable
    data object Profile : Screen()

    @Serializable
    data object Events : Screen()
}
