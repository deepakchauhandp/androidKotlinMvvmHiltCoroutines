package com.dpassets.codebase.data.models.user.register_user.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterUserRequestModel(

    @field:SerializedName("countryCode")
    val countryCode: String,

    @field:SerializedName("emailID")
    val emailID: String,

    @field:SerializedName("firstName")
    val firstName: String,

    @field:SerializedName("lastName")
    val lastName: String,

    @field:SerializedName("mobileCode")
    val mobileCode: String,

    @field:SerializedName("mobileNumber")
    val mobileNumber: String,

    @field:SerializedName("profileImage")
    val profileImage: String,

    @field:SerializedName("langCode")
    val langCode: String

) : Parcelable
