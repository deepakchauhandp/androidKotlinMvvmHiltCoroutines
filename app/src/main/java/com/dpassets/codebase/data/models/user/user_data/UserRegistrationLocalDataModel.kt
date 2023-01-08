package com.dpassets.codebase.data.models.user.user_data

import java.io.Serializable

data class UserRegistrationLocalDataModel(
    var mobileCode : String,
    var mobileNumber : String,
    var countryCode : String,
    var isExistingUser : Boolean
) : Serializable
