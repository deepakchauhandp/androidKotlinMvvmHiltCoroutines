package com.dpassets.codebase.data.models.otp.resend_otp.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.dpassets.codebase.data.models.base.BaseResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResendOtpResponseModel(

    @field:SerializedName("Result")
    val Result: ResendOtpResultModel

) : Parcelable, BaseResponse()