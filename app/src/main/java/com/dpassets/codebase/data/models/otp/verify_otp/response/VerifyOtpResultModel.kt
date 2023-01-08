package com.dpassets.codebase.data.models.otp.verify_otp.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VerifyOtpResultModel(

    @field:SerializedName("api_Token")
    val apiToken: String?,

    @field:SerializedName("country_Code")
    val countryCode: String?,

    @field:SerializedName("customer_Id")
    val customer_Id: String?,

    @field:SerializedName("currency_Symbol")
    val currency_Symbol: String?,

    @field:SerializedName("wallet_Amount")
    val wallet_Amount: String?,

    @field:SerializedName("email_ID")
    val emailID: String?,

    @field:SerializedName("first_Name")
    val firstName: String?,

    @field:SerializedName("isOtpMatched")
    val isOtpMatched: String?,

    @field:SerializedName("last_Name")
    val lastName: String?,

    @field:SerializedName("profile_Image")
    val profileImage: String?

): Parcelable