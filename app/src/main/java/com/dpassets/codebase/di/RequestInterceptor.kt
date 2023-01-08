package com.dpassets.codebase.di

import android.util.Log
import com.dpassets.codebase.constants.PreferenceConstants
import com.dpassets.codebase.utills.AppPrefs
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestInterceptor @Inject constructor(
    private val preferencesHelper: AppPrefs,
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var newRequest: Request = chain.request()

        newRequest = newRequest.newBuilder()
            .addHeader(
                "Authorization",
                "Bearer ${preferencesHelper.getAppPrefs().getString(PreferenceConstants.N_E_K_O_T, null).toString()}")
            .build()

        Log.d(
            "OkHttp", String.format(
                "--> Sending request %s on %s%n%s",
                newRequest.url,
                chain.connection(),
                newRequest.headers
            )
        )
        return chain.proceed(newRequest)
    }
}