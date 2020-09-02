package com.example.testdrawableapplication

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class TextDrawableApp : Application(){
    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}