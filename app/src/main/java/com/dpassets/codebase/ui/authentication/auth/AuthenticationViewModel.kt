package com.dpassets.codebase.ui.authentication.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpassets.codebase.constants.EventTask
import com.dpassets.codebase.data.models.otp.generate_otp.request.GenerateOtpRequestModel
import com.dpassets.codebase.data.models.otp.generate_otp.response.GenerateOtpResponseModel
import com.dpassets.codebase.data.repository.AuthenticationRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val repository: AuthenticationRepository?
) : ViewModel() {

    private val _response: MutableLiveData<EventTask<GenerateOtpResponseModel>> = MutableLiveData()
    val response: LiveData<EventTask<GenerateOtpResponseModel>> = _response
    private var job: Job? = null

    fun generateOTP(generateOtpRequestModel: GenerateOtpRequestModel) {
        job = viewModelScope.launch {
            repository?.generateOTP(generateOtpRequestModel)?.collect { values ->
                _response.value = values
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
