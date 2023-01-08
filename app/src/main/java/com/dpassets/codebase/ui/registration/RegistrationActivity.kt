package com.dpassets.codebase.ui.registration

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.dpassets.codebase.constants.EventTask
import com.dpassets.codebase.constants.GlobalVariables
import com.dpassets.codebase.constants.IntentConstants
import com.dpassets.codebase.constants.Task
import com.dpassets.codebase.data.models.RegisterUserResponseModel
import com.dpassets.codebase.data.models.user.register_user.request.RegisterUserRequestModel
import com.dpassets.codebase.data.models.user.user_data.UserRegistrationLocalDataModel
import com.dpassets.codebase.ui.base.BaseActivity
import com.dpassets.codebase.ui.dashboard.DashboardActivity
import com.dpassets.codebase.utills.Alerts
import com.dpassets.codebase.utills.LocaleHelper
import com.dpassets.codebase.utills.SnackBarUtil
import com.dpassets.codebase.R
import com.dpassets.codebase.databinding.ActivityRegistrationBinding


class RegistrationActivity : BaseActivity() {

    private val viewModel: RegistrationViewModel by viewModels()
    private lateinit var binding: ActivityRegistrationBinding
    private var firstName = ""
    private var lastName = ""
    private var emailAddress = ""
    private var profileImagePath = "/profileImage/"
    private lateinit var userRegistrationLocalDataModel : UserRegistrationLocalDataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentData()
        setLabels()
        clickListener()
    }
    override fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        observeResponse()
    }

    private fun getIntentData() {
        if (intent != null) {
            if (intent.getSerializableExtra(IntentConstants.USER_REGISTER_LOCAL_MODEL) != null) {
                userRegistrationLocalDataModel = (intent.getSerializableExtra(IntentConstants.USER_REGISTER_LOCAL_MODEL) as UserRegistrationLocalDataModel)
            }
        }
    }

    private fun setLabels() {
        binding.apply {
            tvLanguage.text = getSelectedLanguageCode()
            userMobileNumber = "${userRegistrationLocalDataModel.mobileCode} ${userRegistrationLocalDataModel.mobileNumber}"
        }
    }

    private fun clickListener() {
        binding.apply {
            tvLanguage.setOnClickListener {
                Alerts.selectLanguageDialog(this@RegistrationActivity, object : Alerts.LanguageCodeCallback {
                    override fun action(languageCode: String) {
                        languageTextviewLogic(languageCode)
                    }
                })
            }
            llRegister.setOnClickListener {
                if(isAllFilledDataValid()) {
                    val registerUserRequestModel = RegisterUserRequestModel(userRegistrationLocalDataModel.countryCode, emailAddress, firstName, lastName, userRegistrationLocalDataModel.mobileCode, userRegistrationLocalDataModel.mobileNumber, profileImagePath, getSelectedLanguageCode())
                    viewModel.registerUser(registerUserRequestModel)
                } else {
                    SnackBarUtil.showErrorSnackBar(llRegister, getString(R.string.error_message_invalid_input_data))
                }
            }

            llUserProfilePhoto.setOnClickListener {
                pickImage()
            }
        }
    }

    private fun isAllFilledDataValid() : Boolean {
        var isValid = false
        binding.apply {
            firstName = acFirstName.text.toString()
            lastName = acLastName.text.toString()
            emailAddress = acEmail.text.toString()
            if(firstName.isNotEmpty() && lastName.isNotEmpty() && isValidEmail(emailAddress) && profileImagePath.isNotEmpty() && chkTerms.isChecked) {
                isValid = true
            }
        }
        return isValid
    }

    private fun languageTextviewLogic(languageCode : String) {
        setSelectedLanguageCode(languageCode)
        val context = LocaleHelper.setLocale(this@RegistrationActivity, getLocale())
        binding.apply {

        }
    }

    private fun pickImage() {
        Alerts.pickImageDialog(this, object : Alerts.AlertMultipleCallback {
            override fun action(action: Int) {
                when (action) {
                    1 -> {
                        captureImage()
                    }
                    2 -> {
                        pickFromGallery()
                    }
                }
            }
        })
    }

    private fun captureImage() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    resultLauncherForCamera.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                }

                override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
                    resultLauncherForCamera.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {}

            }).check()

    }

    private fun pickFromGallery() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                @RequiresApi(Build.VERSION_CODES.P)
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    resultLauncherForGallery.launch(Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT))
                }

                @RequiresApi(Build.VERSION_CODES.P)
                override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
                    resultLauncherForGallery.launch(Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT))
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {}

            }).check()
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
                        Task.REGISTER_USER -> {
                            val registerUserResponseModel = response.data as RegisterUserResponseModel
                            if(registerUserResponseModel.Message_Code == GlobalVariables.SUCCESS_RESP0NSE) {
                                    openDashboardActivity()
                            } else {
                                SnackBarUtil.showErrorSnackBar(binding.llRegister, registerUserResponseModel.Message)
                            }
                        }
                        else -> {}
                    }
                }

                is EventTask.Error -> {
                    binding.isProgressBarEnable = false
                    binding.rlMain.isUserInteractionEnabled(true)
                    SnackBarUtil.showErrorSnackBar(binding.llRegister,response.message.toString())
                }
            }
        }
    }

    private fun openDashboardActivity() {
        val intent = Intent(this@RegistrationActivity, DashboardActivity::class.java)
        intent.apply {
            putExtra(IntentConstants.USER_REGISTER_LOCAL_MODEL, userRegistrationLocalDataModel)
        }.also { openActivityFromIntent(intent) }
    }

    var resultLauncherForCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val bitmap = data?.extras?.get("data") as Bitmap
            binding.ivUserProfilePhoto.setImageBitmap(bitmap)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    var resultLauncherForGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val contentURI: Uri? = result.data?.data
            val source = contentURI?.let { ImageDecoder.createSource(this.contentResolver, it) }
            val bitmap = source?.let { ImageDecoder.decodeBitmap(it) }
            binding.ivUserProfilePhoto.setImageBitmap(bitmap)
        }
    }
}