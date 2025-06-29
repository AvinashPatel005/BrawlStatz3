package com.kal.brawlstatz3.feature.profile

import android.content.Context
import com.kal.brawlstatz3.feature.brawlers.BrawlerUiEvent

sealed class ProfileUiEvent {
        data class InputTagValueChanged(val tag: String) : ProfileUiEvent()
        data class ItemDelete(val tag:String) : ProfileUiEvent()
    data class InitialTagValue(val tag:String): ProfileUiEvent()
    data object ItemAdd : ProfileUiEvent()
    data object AddClicked : ProfileUiEvent()
    data object Refresh : ProfileUiEvent()
    data object Reload : ProfileUiEvent()
    data class SelectTag(val tag :String) : ProfileUiEvent()
        data object DeleteClicked : ProfileUiEvent()
        data object MenuStart : ProfileUiEvent()
        data object MenuDismiss : ProfileUiEvent()
}