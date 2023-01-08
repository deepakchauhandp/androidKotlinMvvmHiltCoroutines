package com.dpassets.codebase.data.models.user.register_user.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterUserResultModel(

    @field:SerializedName("country_Code")
    val country_Code: String,

    @field:SerializedName("email_ID")
    val email_ID: String,

    @field:SerializedName("first_Name")
    val first_Name: String,

    @field:SerializedName("last_Name")
    val last_Name: String,

    @field:SerializedName("profile_Image")
    val profile_Image: String
) : Parcelable