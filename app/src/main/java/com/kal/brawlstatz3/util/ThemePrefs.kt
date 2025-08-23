package com.kal.brawlstatz3.util

import android.content.Context
import com.kal.brawlstatz3.ui.theme.AppTheme
import androidx.core.content.edit

class ThemePrefs(context: Context) {
    private val prefs = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)

    fun saveTheme(theme: AppTheme) {
        prefs.edit { putInt("theme_key", theme.ordinal) }
    }

    fun getTheme(): AppTheme {
        val ordinal = prefs.getInt("theme_key", AppTheme.DYNAMIC.ordinal)
        return AppTheme.entries[ordinal]
    }
}
