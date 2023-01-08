package com.dpassets.codebase.helper

import android.content.Context
import android.net.ConnectivityManager

class CheckInternetConnection() {

    fun isNetworkAvailable(): Boolean {
        return isOnline()
    }

    companion object {
        fun isInternetConnected(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

    private fun isOnline(): Boolean {
        try {
            val p1 = Runtime.getRuntime().exec("ping -c 1 www.google.com")
            val returnVal = p1.waitFor()
            return returnVal == 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}
