package com.dpassets.codebase.data.models.otp.generate_otp.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.dpassets.codebase.data.models.base.BaseResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenerateOtpResponseModel(

    @field:SerializedName("Result")
    val Result: GenerateOtpResultResponse

) : Parcelable, BaseResponse()
