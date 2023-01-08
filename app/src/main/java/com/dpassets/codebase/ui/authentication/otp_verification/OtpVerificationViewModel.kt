package com.dpassets.codebase.ui.authentication.otp_verification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpassets.codebase.constants.EventTask
import com.dpassets.codebase.data.models.otp.resend_otp.request.ResendOtpRequestModel
import com.dpassets.codebase.data.models.otp.verify_otp.request.VerifyOtpRequestModel
import com.dpassets.codebase.data.repository.OtpVerificationRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@HiltViewModel
class OtpVerificationViewModel @Inject constructor(private val repository: OtpVerificationRepository
) : ViewModel() {
    private val _response: MutableLiveData<EventTask<*>> = MutableLiveData()
    val response: LiveData<EventTask<*>> = _response
    private var job: Job? = null

    fun verifyOTP(verifyOtpRequestModel: VerifyOtpRequestModel) {
        job = viewModelScope.launch {
            repository.verifyOTP(verifyOtpRequestModel).collect { values ->
                _response.value = values
            }
        }
    }

    fun resendOTP(resendOtpRequestModel: ResendOtpRequestModel) {
        job = viewModelScope.launch {
            repository.resendOTP(resendOtpRequestModel).collect { values ->
                _response.value = values
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
