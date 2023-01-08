package com.dpassets.codebase.data.models.otp.resend_otp.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResendOtpRequestModel(

    @field:SerializedName("mobileCode")
    val mobileCode: String,

    @field:SerializedName("mobileNumber")
    val mobileNumber: String,

    @field:SerializedName("langCode")
    val langCode: String
) : Parcelable