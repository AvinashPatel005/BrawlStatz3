package com.kal.brawlstatz3.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit

fun saveStringListToPrefs(context: Context, key: String, list: List<String>) {
    val sharedPrefs: SharedPreferences = context.getSharedPreferences("dtop", Context.MODE_PRIVATE)
    sharedPrefs.edit() {
        val json = Gson().toJson(list)
        putString(key, json)
        apply()
    }
}

fun getStringListFromPrefs(context: Context, key: String): List<String> {
    val sharedPrefs: SharedPreferences = context.getSharedPreferences("dtop", Context.MODE_PRIVATE)
    val json = sharedPrefs.getString(key, null)
    return if (json != null) {
        val type = object : TypeToken<List<String>>() {}.type
        Gson().fromJson(json, type)
    } else {
        emptyList()
    }
}


fun saveStringToPrefs(context: Context, key: String, value: String) {
    val sharedPrefs: SharedPreferences = context.getSharedPreferences("dtop", Context.MODE_PRIVATE)
    sharedPrefs.edit() { putString(key, value) }
}

fun getStringFromPrefs(context: Context, key: String, defaultValue: String = ""): String? {
    val sharedPrefs: SharedPreferences = context.getSharedPreferences("dtop", Context.MODE_PRIVATE)
    return sharedPrefs.getString(key, defaultValue)
}