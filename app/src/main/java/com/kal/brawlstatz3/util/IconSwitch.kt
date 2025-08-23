package com.kal.brawlstatz3.util

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

fun getActiveAlias(context: Context): String? {
    val pm = context.packageManager
    val packageName = context.packageName

    val aliases = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES or PackageManager.GET_DISABLED_COMPONENTS)
        .activities
        ?.filter { it.targetActivity != null } // only aliases
        ?: emptyList()

    val active = aliases.find {
        pm.getComponentEnabledSetting(android.content.ComponentName(packageName, it.name)) ==
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED
    }

    return active?.name
}
fun switchIcon(enableAlias: String, context: Context) {
    val pm = context.packageManager
    val packageName = context.packageName

    val aliases = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES or PackageManager.GET_DISABLED_COMPONENTS)
        .activities
        ?.filter { it.targetActivity != null }
        ?.map { it.name }
        ?: emptyList()

    aliases.forEach { aliasName ->
        val component = ComponentName(packageName, aliasName)
        val state = if (aliasName.equals(enableAlias)) {
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        } else {
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED
        }

        pm.setComponentEnabledSetting(
            component,
            state,
            PackageManager.DONT_KILL_APP
        )
    }
}