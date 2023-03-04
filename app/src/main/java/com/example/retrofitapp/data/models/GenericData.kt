package com.example.retrofitapp.data.models

data class GenericData<out T>(
    val success:Boolean,
    val code: Int,
    val message:String,
    val payload: T
)