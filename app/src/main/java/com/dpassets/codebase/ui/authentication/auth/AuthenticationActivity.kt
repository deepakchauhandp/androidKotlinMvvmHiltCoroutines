package com.dpassets.codebase.ui.authentication.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.dpassets.codebase.R
import com.dpassets.codebase.constants.EventTask
import com.dpassets.codebase.constants.GlobalVariables
import com.dpassets.codebase.constants.IntentConstants
import com.dpassets.codebase.data.models.otp.generate_otp.request.GenerateOtpRequestModel
import com.dpassets.codebase.data.models.user.user_data.UserRegistrationLocalDataModel
import com.dpassets.codebase.databinding.ActivityAuthenticationBinding
import com.dpassets.codebase.ui.authentication.otp_verification.OtpVerificationActivity
import com.dpassets.codebase.ui.base.BaseActivity
import com.dpassets.codebase.utills.SnackBarUtil
import dagger.hilt.android.AndroidEntryPoint
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil

@AndroidEntryPoint
class AuthenticationActivity : BaseActivity() {

    private val viewModel: AuthenticationViewModel by viewModels()
    private lateinit var binding: ActivityAuthenticationBinding
    private var mobileNumber = ""
    private var mobileCode = ""
    private var countryCode = ""
    private lateinit var phoneNumberUtil : PhoneNumberUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickListener()
    }

    override fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication)
        observeOtpResponse()
    }

    private fun clickListener() {
         binding.apply {

             llProceed.setOnClickListener {
                 if(isValidMobileNumber()) {
                     rlMain.isUserInteractionEnabled(false)
                     acMobileNumber.clearFocus()
                     val generateOtpRequestModel = GenerateOtpRequestModel(
                          getSelectedLanguageCode(),
                          mobileCode,
                          mobileNumber,"d123", "1", "1.2.34", "dv123"
                      )
                     viewModel.generateOTP(generateOtpRequestModel)
                 } else {
                    SnackBarUtil.showErrorSnackBar(llProceed,resources.getString(R.string.error_message_invalid_mobile))
                 }
             }
         }
    }

    private fun isValidMobileNumber() : Boolean {
        var isValid = false
        phoneNumberUtil = PhoneNumberUtil.createInstance(this)
        if(this::phoneNumberUtil.isInitialized) {
            mobileNumber = binding.acMobileNumber.text.toString()
            mobileCode = binding.ccp.selectedCountryCodeWithPlus
            countryCode = binding.ccp.selectedCountryNameCode
            val phone = phoneNumberUtil.parse(mobileNumber, binding.ccp.selectedCountryNameCode)
            isValid =  phoneNumberUtil.isValidNumber(phone) && mobileNumber.isNotBlank() && mobileCode.isNotBlank()
        }
        return isValid
    }

    private fun observeOtpResponse() {
        viewModel.response.observe(this) { response ->
            when (response) {
                is EventTask.Loading -> {
                    binding.isProgressBarEnable = true
                }

                is EventTask.Success -> {
                    binding.isProgressBarEnable = false
                    binding.rlMain.isUserInteractionEnabled(true)
                    response.data?.let {
                        if(response.data.Message_Code == GlobalVariables.SUCCESS_RESP0NSE) {
                            val userRegistrationLocalDataModel = UserRegistrationLocalDataModel(mobileCode, mobileNumber, countryCode, response.data.Result.isExistingUser == GlobalVariables.MATCHED_RESPONSE)
                            openOtpVerificationActivity(userRegistrationLocalDataModel)
                        }
                    }
                }

                is EventTask.Error -> {
                    binding.isProgressBarEnable = false
                    binding.rlMain.isUserInteractionEnabled(true)
                    SnackBarUtil.showErrorSnackBar(binding.llProceed,response.message.toString())
                }
            }
        }
    }

    private fun openOtpVerificationActivity(userRegistrationLocalDataModel: UserRegistrationLocalDataModel) {
        val intent = Intent(this@AuthenticationActivity, OtpVerificationActivity::class.java)
        intent.apply {
            putExtra(IntentConstants.USER_REGISTER_LOCAL_MODEL, userRegistrationLocalDataModel)
        }.also { openActivityFromIntent(intent) }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}