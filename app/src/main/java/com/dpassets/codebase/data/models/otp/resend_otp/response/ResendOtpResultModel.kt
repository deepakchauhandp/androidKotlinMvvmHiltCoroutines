package com.dpassets.codebase.data.models.otp.resend_otp.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResendOtpResultModel(

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String

) : Parcelable
