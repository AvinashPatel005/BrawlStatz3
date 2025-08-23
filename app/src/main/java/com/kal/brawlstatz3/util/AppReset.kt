package com.kal.brawlstatz3.util

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context


fun appReset(context: Context){
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    activityManager.clearApplicationUserData()
}