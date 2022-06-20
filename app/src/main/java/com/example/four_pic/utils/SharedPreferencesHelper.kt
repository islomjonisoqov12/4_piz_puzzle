package com.example.four_pic.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import java.util.*


class SharedPreferencesHelper(var context: Context) {

    private var preferences: SharedPreferences =
        context.getSharedPreferences("APP_PREFERENCES", MODE_PRIVATE)

    private lateinit var editor: SharedPreferences.Editor


    

    fun setNightMode(isNightMode: Boolean) {
        editor = preferences.edit()
        editor.putBoolean("IS_NIGHT", isNightMode)
        editor.apply()
    }
    fun setDifficulty(difficult:String){
        editor = preferences.edit()
        editor.putString("DIFFICULT", difficult)
        editor.apply()
    }
    fun getDifficulty() = preferences.getString("DIFFICULT", "EASY")

    fun setLevel(level:Int){
        editor = preferences.edit()
        editor.putInt("LEVEL",level)
        editor.apply()
    }

    fun getLevel() = preferences.getInt("LEVEL",0)

    fun setCoin(coin:Int){
        editor = preferences.edit()
        editor.putInt("COIN",coin)
        editor.apply()
    }

    fun getCoin() = preferences.getInt("COIN",0)

    private fun updateResources(context: Context, language: String): Context? {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)//bu joyni uchirib kor
        return context.createConfigurationContext(configuration)
    }


    private fun updateResourcesLegacy(context: Context, language: String): Context? {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }

    // Set Username
    fun setUserName(UserName : String){
        editor = preferences.edit()
        editor.putString("USERNAME", UserName)
        editor.apply()
    }

    // get UserName
    fun getUserName() = preferences.getString("USERNAME","")

    fun setMusicMode(MusicMode:Boolean){
        editor = preferences.edit()
        editor.putBoolean("MUSIC_MODE",MusicMode)
        editor.apply()
    }

    fun getMusicMode() = preferences.getBoolean("MUSIC_MODE",true)

    fun setLanguage(language:String){
        editor = preferences.edit()
        editor.putString("LANGUAGE", language)
        editor.apply()
    }
    fun getLanguage() = preferences.getString("LANGUAGE", "ru")

    fun setSound(isplayed:Boolean) {
        editor = preferences.edit()
        editor.putBoolean("ISPLAYED",isplayed)
        editor.apply()
    }
    fun getSound() = preferences.getBoolean("ISPLAYED", false)
}