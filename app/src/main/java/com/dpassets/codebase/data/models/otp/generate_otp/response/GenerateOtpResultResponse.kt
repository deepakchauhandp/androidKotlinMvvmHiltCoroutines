package com.dpassets.codebase.data.models.otp.generate_otp.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenerateOtpResultResponse(

    @field:SerializedName("isExistingUser")
    val isExistingUser: String,

    @field:SerializedName("otp")
    val otp: String

)  : Parcelable