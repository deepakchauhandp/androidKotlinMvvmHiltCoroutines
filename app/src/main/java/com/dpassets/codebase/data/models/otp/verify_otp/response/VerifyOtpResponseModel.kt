package com.dpassets.codebase.data.models.otp.verify_otp.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.dpassets.codebase.data.models.base.BaseResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class VerifyOtpResponseModel(

    @field:SerializedName("Result")
    val Result: VerifyOtpResultModel

) : Parcelable, BaseResponse()