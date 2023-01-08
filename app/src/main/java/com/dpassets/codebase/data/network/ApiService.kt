package com.dpassets.codebase.data.network

import com.dpassets.codebase.data.models.RegisterUserResponseModel
import com.dpassets.codebase.data.models.otp.generate_otp.request.GenerateOtpRequestModel
import com.dpassets.codebase.data.models.otp.generate_otp.response.GenerateOtpResponseModel
import com.dpassets.codebase.data.models.otp.resend_otp.request.ResendOtpRequestModel
import com.dpassets.codebase.data.models.otp.resend_otp.response.ResendOtpResponseModel
import com.dpassets.codebase.data.models.otp.verify_otp.request.VerifyOtpRequestModel
import com.dpassets.codebase.data.models.otp.verify_otp.response.VerifyOtpResponseModel
import com.dpassets.codebase.data.models.user.register_user.request.RegisterUserRequestModel
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ApiService {

    @POST("generateOTP")
    suspend fun generateOTP(@Body generateOtpRequestModel: GenerateOtpRequestModel): GenerateOtpResponseModel

    @POST("resendOTP")
    suspend fun resendOTP(@Body resendOtpRequestModel: ResendOtpRequestModel): ResendOtpResponseModel

    @POST("verifyOTP")
    suspend fun verifyOTP(@Body verifyOtpRequestModel: VerifyOtpRequestModel) : VerifyOtpResponseModel

    @POST("registration")
    suspend fun registerUser(@Body registerUserRequestModel: RegisterUserRequestModel) : RegisterUserResponseModel
}