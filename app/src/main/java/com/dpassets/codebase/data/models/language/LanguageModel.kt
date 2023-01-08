package com.dpassets.codebase.data.models.language

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LanguageModel(
    val language : String,
    val languageCode : String
) : Parcelable

