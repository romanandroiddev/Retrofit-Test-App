package com.example.retrofitapp.data.models

import com.google.gson.annotations.SerializedName

data class RegisterRequestBodyData(
    val name: String,
    val password: String,
    val phone: String
)