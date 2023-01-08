package com.dpassets.codebase.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.dpassets.codebase.data.models.base.BaseResponse
import com.dpassets.codebase.data.models.user.register_user.response.RegisterUserResultModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterUserResponseModel(
    @field:SerializedName("Result")
    val Result: RegisterUserResultModel

) : Parcelable, BaseResponse()
