package com.dpassets.codebase.data.repository

import com.dpassets.codebase.constants.EventTask
import com.dpassets.codebase.constants.Status
import com.dpassets.codebase.constants.Task
import com.dpassets.codebase.data.models.otp.generate_otp.request.GenerateOtpRequestModel
import com.dpassets.codebase.data.models.otp.generate_otp.response.GenerateOtpResponseModel
import com.dpassets.codebase.data.network.ApiService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class AuthenticationRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun generateOTP(generateOtpRequestModel: GenerateOtpRequestModel): Flow<EventTask<GenerateOtpResponseModel>> = flow {
        emit(EventTask.Loading(Task.GENERATE_OTP))
        try {
            val response = apiService.generateOTP(generateOtpRequestModel)
            emit(EventTask.Success(response, Task.GENERATE_OTP))
        } catch (e: Exception) {
            emit(EventTask.Error(e.toString(), Status.ERROR, Task.GENERATE_OTP))
        }
    }
}
