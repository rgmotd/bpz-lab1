package com.example.bpz_lab1

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

const val BUNDLE_IS_ACTIVATED = "bundle is activated"

class Prefs(context: Context) {

    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(
        "App prefs",
        Context.MODE_PRIVATE
    )

    fun getIsActivated() = sharedPrefs.getBoolean(BUNDLE_IS_ACTIVATED, false)

    fun setIsActivated(activated: Boolean) {
        sharedPrefs.edit().putBoolean(BUNDLE_IS_ACTIVATED, activated).apply()
    }
}