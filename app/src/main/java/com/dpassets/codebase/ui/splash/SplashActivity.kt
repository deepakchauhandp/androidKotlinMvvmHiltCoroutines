package com.dpassets.codebase.ui.splash

import android.annotation.SuppressLint
import android.os.*
import androidx.databinding.DataBindingUtil
import com.dpassets.codebase.constants.PreferenceConstants
import com.dpassets.codebase.ui.authentication.auth.AuthenticationActivity
import com.dpassets.codebase.ui.base.BaseActivity
import com.dpassets.codebase.ui.dashboard.DashboardActivity
import com.dpassets.codebase.R
import com.dpassets.codebase.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    @androidx.annotation.RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delaySplashScreen()
    }

    override fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
    }

    private fun delaySplashScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            manageOpeningActivity()
        }, 2500)
    }

    private fun manageOpeningActivity() {
        if (appPref.getAppPrefs().getBoolean(PreferenceConstants.IS_REGISTRATION_COMP, false)) {
            openDashboardActivity()
        } else {
            openAuthenticationActivity()
        }
    }

    private fun openAuthenticationActivity() {
        openActivity(AuthenticationActivity::class.java)
    }

    private fun openDashboardActivity() {
        openActivity(DashboardActivity::class.java)
    }
}