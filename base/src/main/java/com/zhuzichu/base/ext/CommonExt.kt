package com.zhuzichu.base.ext

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.LocaleList
import java.util.*

@Suppress("UNCHECKED_CAST")
fun <T> cast(obj: Any?): T {
    return obj as T
}

@Suppress("UNCHECKED_CAST")
fun <T> Any?.toCast(): T {
    return this as T
}

@Suppress("DEPRECATION")
@SuppressLint("ObsoleteSdkInt")
fun Context.localeContextWrapper(newLocale: Locale): ContextWrapper {
    var context: Context = this
    val config = this.resources.configuration

    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
            Locale.setDefault(newLocale)
            config.setLocale(newLocale)
            val localeList = LocaleList(newLocale)
            LocaleList.setDefault(localeList)
            config.setLocales(localeList)
            context = this.createConfigurationContext(config)
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 -> {
            config.setLocale(newLocale)
            context = this.createConfigurationContext(config)
        }
        else -> {
            config.locale = newLocale
            this.resources.updateConfiguration(config, this.resources.displayMetrics)
        }
    }
    return ContextWrapper(context)
}