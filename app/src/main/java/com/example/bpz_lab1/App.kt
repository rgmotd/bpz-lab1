package com.example.bpz_lab1

import android.app.Application

class App : Application() {
    lateinit var sharedPrefs: Prefs

    override fun onCreate() {
        super.onCreate()
        sharedPrefs = Prefs(this)
    }
}