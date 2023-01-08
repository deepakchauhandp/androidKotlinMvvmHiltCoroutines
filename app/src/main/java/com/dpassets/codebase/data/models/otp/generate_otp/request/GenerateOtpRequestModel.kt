package com.dpassets.codebase.data.models.otp.generate_otp.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Parcelize
data class GenerateOtpRequestModel(

    @field:SerializedName("langCode")
    var langCode: String,

    @field:SerializedName("mobileCode")
    var mobileCode: String,

    @field:SerializedName("mobileNumber")
    var mobileNumber: String,

    @field:SerializedName("deviceId")
    var deviceId: String,

    @field:SerializedName("deviceType")
    var deviceType: String,

    @field:SerializedName("deviceOSVersion")
    var deviceOSVersion: String,

    @field:SerializedName("buildVersion")
    var buildVersion: String

) : Parcelable
