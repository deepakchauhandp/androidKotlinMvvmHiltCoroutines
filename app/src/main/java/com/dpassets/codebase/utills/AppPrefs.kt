package com.dpassets.codebase.utills

import android.content.Context
import android.content.SharedPreferences
import com.dpassets.codebase.constants.PreferenceConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPrefs @Inject constructor(@ApplicationContext context: Context){

    private var prefs = context.getSharedPreferences(PreferenceConstants.PREF_NAME, Context.MODE_PRIVATE)

    fun getAppPrefs() : SharedPreferences {
        return prefs
    }

    fun setPreferences(key : String, value :Any) {
        val editor = prefs.edit()
        try {
            when (value) {
                is String -> {
                    editor?.putString(key, value)
                }
                is Boolean -> {
                    editor?.putBoolean(key, value)
                }
                is Long -> {
                    editor?.putLong(key, value)
                }
                is Int -> {
                    editor?.putInt(key, value)
                }
                is Double -> {
                    editor?.putLong(key, java.lang.Double.doubleToLongBits(value))
                }
                is Float -> {
                    editor?.putFloat(key, value)
                }
            }
            editor.apply()
        } catch (e : Exception) {

        }
    }
}
