package com.dpassets.codebase.data.models.base

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
open class BaseResponse(

    @field:SerializedName("Message_Code")
    val Message_Code: Int = 0,

    @field:SerializedName("Message")
    val Message: String = ""
) : Parcelable