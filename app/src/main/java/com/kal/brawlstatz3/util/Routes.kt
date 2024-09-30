package com.kal.brawlstatz3.util
import kotlinx.serialization.Serializable
sealed class Routes{
    @Serializable
    data object Brawlers:Routes()

    @Serializable
    data object Meta:Routes()

    @Serializable
    data object Profile:Routes()

    @Serializable
    data object Events:Routes()
}