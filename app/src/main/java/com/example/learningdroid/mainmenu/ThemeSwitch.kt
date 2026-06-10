package com.example.learningdroid.mainmenu

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.learningdroid.settingpref.SettingPref
import com.example.learningdroid.settingpref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ThemeSwitch: Application() {

    override fun onCreate() {
        super.onCreate()

        val pref = SettingPref.getInstance(dataStore)

        val isDarkMode = runBlocking {
            pref.getThemeSetting().first()
        }

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}