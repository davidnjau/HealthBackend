package com.example.healthbackend.webapp.helperclass

import com.example.healthbackend.authentication.entity.StaffDetails


data class Results(
    val statusCode: Int,
    val details: Any
)
data class ErrorMessage(
    val error: String
)
data class SuccessMessage(
    val details: String

)
data class SuccessfulLogin(
    val userDetails: StaffDetails,
    val accessToken : String
)
data class LoginResponse(

    val accessToken:String,
    val userId:String,
    val fullNames:String,
    val emailAddress:String,
    val roles:List<String>,
)
