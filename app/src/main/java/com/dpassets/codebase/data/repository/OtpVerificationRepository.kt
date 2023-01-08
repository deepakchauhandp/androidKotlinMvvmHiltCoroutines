package com.dpassets.codebase.data.repository

import com.dpassets.codebase.constants.EventTask
import com.dpassets.codebase.constants.Status
import com.dpassets.codebase.constants.Task
import com.dpassets.codebase.data.models.otp.resend_otp.request.ResendOtpRequestModel
import com.dpassets.codebase.data.models.otp.resend_otp.response.ResendOtpResponseModel
import com.dpassets.codebase.data.models.otp.verify_otp.request.VerifyOtpRequestModel
import com.dpassets.codebase.data.models.otp.verify_otp.response.VerifyOtpResponseModel
import com.dpassets.codebase.data.network.ApiService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class OtpVerificationRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun verifyOTP(verifyOtpRequestModel: VerifyOtpRequestModel): Flow<EventTask<VerifyOtpResponseModel>> = flow {
        emit(EventTask.Loading(Task.VERIFY_OTP))
        try {
            val response = apiService.verifyOTP(verifyOtpRequestModel)
            emit(EventTask.Success(response, Task.VERIFY_OTP))
        } catch (e: Exception) {
            emit(EventTask.Error(e.toString(), Status.ERROR, Task.VERIFY_OTP))
        }
    }

    suspend fun resendOTP(resendOtpRequestModel: ResendOtpRequestModel): Flow<EventTask<ResendOtpResponseModel>> = flow {
        emit(EventTask.Loading(Task.RESEND_OTP))
        try {
            val response = apiService.resendOTP(resendOtpRequestModel)
            emit(EventTask.Success(response, Task.RESEND_OTP))
        } catch (e: Exception) {
            emit(EventTask.Error(e.toString(), Status.ERROR, Task.RESEND_OTP))
        }
    }
}
