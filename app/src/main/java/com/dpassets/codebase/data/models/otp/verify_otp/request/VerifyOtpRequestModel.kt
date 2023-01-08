package com.dpassets.codebase.data.models.otp.verify_otp.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VerifyOtpRequestModel(

    @field:SerializedName("mobileCode")
    var mobileCode: String,

    @field:SerializedName("langCode")
    var langCode: String,

    @field:SerializedName("mobileNumber")
    var mobileNumber: String,

    @field:SerializedName("otp")
    var otp: String

) : Parcelable {
    override fun toString(): String {
        return "VerifyOtpRequestModel(mobileCode='$mobileCode', mobileNumber='$mobileNumber', otp='$otp')"
    }
}
