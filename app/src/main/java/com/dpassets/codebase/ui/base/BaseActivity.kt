package com.dpassets.codebase.ui.base

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.dpassets.codebase.R
import com.dpassets.codebase.constants.PreferenceConstants
import com.dpassets.codebase.utills.AppPrefs
import com.dpassets.codebase.utills.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var appPref : AppPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocaleHelper.setLocale(this, getLocale())
        setBinding()
    }

    protected abstract fun setBinding()

    fun getLocale() : Locale {
        val language = appPref.getAppPrefs().getString(PreferenceConstants.LANGUAGE_CODE, null)
        return if(null != language) Locale(language) else Locale(Locale.getDefault().toString())
    }

    fun setSelectedLanguageCode(languageCode : String) {
        appPref.setPreferences(PreferenceConstants.LANGUAGE_CODE, languageCode)
    }

    fun getSelectedLanguageCode() : String {
       return appPref.getAppPrefs().getString(PreferenceConstants.LANGUAGE_CODE, getString(R.string.lang_english_code)).toString()
    }

    fun hideKeyBoard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun View.isUserInteractionEnabled(enabled: Boolean) {
        isEnabled = enabled
        if (this is ViewGroup && this.childCount > 0) {
            this.children.forEach {
                it.isUserInteractionEnabled(enabled)
            }
        }
    }

    fun openActivity(cls : Class<*>) {
        startActivity(Intent(this, cls))
        this.finish()
    }

    fun openActivityFromIntent(intent: Intent) {
        startActivity(intent)
        this.finish()
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}