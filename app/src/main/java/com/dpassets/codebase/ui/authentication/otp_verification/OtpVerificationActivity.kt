package com.dpassets.codebase.ui.authentication.otp_verification

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.KeyEvent.*
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.dpassets.codebase.constants.*
import com.dpassets.codebase.data.models.otp.resend_otp.request.ResendOtpRequestModel
import com.dpassets.codebase.data.models.otp.resend_otp.response.ResendOtpResponseModel
import com.dpassets.codebase.data.models.otp.verify_otp.request.VerifyOtpRequestModel
import com.dpassets.codebase.data.models.otp.verify_otp.response.VerifyOtpResponseModel
import com.dpassets.codebase.data.models.user.user_data.UserRegistrationLocalDataModel
import com.dpassets.codebase.ui.authentication.auth.AuthenticationActivity
import com.dpassets.codebase.ui.base.BaseActivity
import com.dpassets.codebase.ui.dashboard.DashboardActivity
import com.dpassets.codebase.ui.registration.RegistrationActivity
import com.dpassets.codebase.utills.Alerts
import com.dpassets.codebase.utills.LocaleHelper
import com.dpassets.codebase.utills.SnackBarUtil
import com.dpassets.codebase.R
import com.dpassets.codebase.databinding.ActivityOtpVerificationBinding
import kotlin.CharSequence
import kotlin.Int
import kotlin.Long
import kotlin.apply
import kotlin.getValue
import kotlin.toString

class OtpVerificationActivity : BaseActivity() {

    private val viewModel: OtpVerificationViewModel by viewModels()
    private lateinit var binding: ActivityOtpVerificationBinding
    private lateinit var userRegistrationLocalDataModel : UserRegistrationLocalDataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentData()
        clickListener()
    }

    override fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp_verification)
        observeResponse()
    }

    private fun getIntentData() {
        if (intent != null) {
            if (intent.getSerializableExtra(IntentConstants.USER_REGISTER_LOCAL_MODEL) != null) {
                userRegistrationLocalDataModel = intent.getSerializableExtra(IntentConstants.USER_REGISTER_LOCAL_MODEL) as UserRegistrationLocalDataModel
            }
        }
    }

    private fun clickListener() {
        binding.apply {
            acOtp1.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable) {
                    if(s.isNotEmpty()) {
                        acOtp2.requestFocus()
                    }
                }
            })

            acOtp2.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable) {
                    if(s.isNotEmpty()) {
                        acOtp3.requestFocus()
                    }
                }
            })

            acOtp3.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable) {
                    if(s.isNotEmpty()) {
                        acOtp4.requestFocus()
                    }
                }
            })

            acOtp4.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable) {
                    if(s.isNotEmpty()) {
                        acOtp5.requestFocus()
                    }
                }
            })

            acOtp5.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable) {
                    if(s.isNotEmpty()) {
                        hideKeyBoard()
                    }
                }
            })

            tvResend.setOnClickListener {
                val resendOtpRequestModel = ResendOtpRequestModel(userRegistrationLocalDataModel.mobileCode, userRegistrationLocalDataModel.mobileNumber, getSelectedLanguageCode())
                viewModel.resendOTP(resendOtpRequestModel)
            }

            tvChangeMobileNumber.setOnClickListener {
                showBackDialog(1)
            }

            tvEditMobileNumber.setOnClickListener {
                showBackDialog(1)
            }

            llProceed.setOnClickListener {
                lastAutoCompleteLogic()
            }

        }
    }

    private fun lastAutoCompleteLogic() {
        binding.apply {
            acOtp5.clearFocus()
            val otpString = acOtp1.text.toString() + acOtp2.text.toString() + acOtp3.text.toString() + acOtp4.text.toString() + acOtp5.text.toString()
            if(isValidOtp(otpString)) {
                rlMain.isUserInteractionEnabled(false)
                val verifyOtpRequestModel = VerifyOtpRequestModel(
                    userRegistrationLocalDataModel.mobileCode,
                    getSelectedLanguageCode(),
                    userRegistrationLocalDataModel.mobileNumber,
                    otpString
                )
                viewModel.verifyOTP(verifyOtpRequestModel)
            } else {
                SnackBarUtil.showErrorSnackBar(binding.tvEditMobileNumber, getString(R.string.error_valid_otp))
            }
        }
    }

    private fun isValidOtp(otp : String) : Boolean {
         return (otp.isNotEmpty() && otp.length <= 5)
    }

    private fun observeResponse() {
        viewModel.response.observe(this) { response ->
            when (response) {
                is EventTask.Loading -> {
                    binding.isProgressBarEnable = true
                }

                is EventTask.Success -> {
                    binding.isProgressBarEnable = false
                    binding.rlMain.isUserInteractionEnabled(true)
                    when(response.task) {
                        Task.VERIFY_OTP -> {
                            val verifyOtpResponse = response.data as VerifyOtpResponseModel
                            if(verifyOtpResponse.Message_Code == GlobalVariables.SUCCESS_RESP0NSE && verifyOtpResponse.Result.isOtpMatched == GlobalVariables.MATCHED_RESPONSE) {
                                appPref.apply {
                                    setPreferences(PreferenceConstants.N_E_K_O_T, verifyOtpResponse.Result.apiToken ?: "")
                                }
                                if(!userRegistrationLocalDataModel.isExistingUser) {
                                    openDashBoardActivity()
                                } else {
                                    openRegistrationActivity()
                                }
                            } else {
                                SnackBarUtil.showErrorSnackBar(binding.tvEditMobileNumber, verifyOtpResponse.Message)
                            }
                        }
                        Task.RESEND_OTP -> {
                            val resendOtpResponse = response.data as ResendOtpResponseModel
                            if(resendOtpResponse.Message_Code == GlobalVariables.SUCCESS_RESP0NSE && resendOtpResponse.Result.status == GlobalVariables.SUCCESS) {
                                startOtpTimer()
                                SnackBarUtil.showSuccessSnackBar(binding.tvEditMobileNumber, resendOtpResponse.Result.message)
                            } else {
                                SnackBarUtil.showErrorSnackBar(binding.tvEditMobileNumber, resendOtpResponse.Message)
                            }
                        }
                        else -> {}
                    }
                }

                is EventTask.Error -> {
                    binding.isProgressBarEnable = false
                    binding.rlMain.isUserInteractionEnabled(true)
                    SnackBarUtil.showErrorSnackBar(binding.tvEditMobileNumber,response.message.toString())
                }
            }
        }
    }

    private fun clearOtpView() {
        binding.apply {
            acOtp1.setText("")
            acOtp2.setText("")
            acOtp3.setText("")
            acOtp4.setText("")
            acOtp5.setText("")
            acOtp1.requestFocus()
        }
    }

    override fun onBackPressed() {
        showBackDialog(2)
    }

    /*
    @param action is open Authentication activity if value is 1 and finish all activity if the value is 2
     */
    private fun showBackDialog(action : Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(if(action == 1) getString(R.string.message_do_you_want_to_go_back) else getString(
                    R.string.message_change_mobile_number))
            .setPositiveButton(getString(R.string.label_yes)) { _, _ ->
                if (action == 1) {
                    openAuthenticationActivity()
                } else if (action == 2) {
                    finishAffinity()
                }
            }
            .setNegativeButton(getString(R.string.label_no)
            ) { _, _ -> }
        builder.create().show()
    }

    private fun startOtpTimer() {
        clearOtpView()
        binding.isResendOtp = true
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val countedSeconds = millisUntilFinished/1000
                binding.otpTimer = "$countedSeconds ${getString(R.string.label_seconds)}"
            }

            override fun onFinish() {
                binding.isResendOtp = false
            }
        }.start()
    }

    private fun openAuthenticationActivity() {
        openActivity(AuthenticationActivity::class.java)
    }

    private fun openRegistrationActivity() {
        val intent = Intent(this@OtpVerificationActivity, RegistrationActivity::class.java)
        intent.apply {
            putExtra(IntentConstants.USER_REGISTER_LOCAL_MODEL, userRegistrationLocalDataModel)
        }.also { openActivityFromIntent(intent) }
    }

    private fun openDashBoardActivity() {
        openActivity(DashboardActivity::class.java)
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if(event?.keyCode == KEYCODE_DEL) {
            binding.apply {
                when {
                    acOtp2.hasFocus() -> {
                        if(acOtp2.text.isEmpty()) {
                            acOtp1.requestFocus()
                            return true
                        }
                    }
                    acOtp3.hasFocus() -> {
                        if(acOtp3.text.isEmpty()) {
                            acOtp2.requestFocus()
                            return true
                        }
                    }
                    acOtp4.hasFocus() -> {
                        if(acOtp4.text.isEmpty()) {
                            acOtp3.requestFocus()
                            return true
                        }
                    }
                    acOtp5.hasFocus() -> {
                        if(acOtp5.text.isEmpty()) {
                            acOtp4.requestFocus()
                            return true
                        }
                    }
                }
            }
        } else if(event?.keyCode == KEYCODE_0 ||
            event?.keyCode == KEYCODE_1 ||
            event?.keyCode == KEYCODE_2 ||
            event?.keyCode == KEYCODE_3 ||
            event?.keyCode == KEYCODE_4 ||
            event?.keyCode == KEYCODE_5 ||
            event?.keyCode == KEYCODE_6 ||
            event?.keyCode == KEYCODE_7 ||
            event?.keyCode == KEYCODE_8 ||
            event?.keyCode == KEYCODE_9) {
            binding.apply {
                when {
                    acOtp1.hasFocus() -> {
                        if(acOtp1.text.isNotEmpty()) {
                            acOtp2.requestFocus()
                            return true
                        }
                    }
                    acOtp2.hasFocus() -> {
                        if(acOtp2.text.isNotEmpty()) {
                            acOtp3.requestFocus()
                            return true
                        }
                    }
                    acOtp3.hasFocus() -> {
                        if(acOtp3.text.isNotEmpty()) {
                            acOtp4.requestFocus()
                            return true
                        }
                    }
                    acOtp4.hasFocus() -> {
                        if(acOtp4.text.isNotEmpty()) {
                            acOtp5.requestFocus()
                            return true
                        }
                    }
                }
            }
        }
        return super.dispatchKeyEvent(event)
    }
}