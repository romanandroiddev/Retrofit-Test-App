package com.example.retrofitapp.data.remote

import com.example.retrofitapp.data.models.GenericData
import com.example.retrofitapp.data.models.RegisterReponseData
import com.example.retrofitapp.data.models.RegisterRequestBodyData
import com.example.retrofitapp.data.models.TaskData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface TodoApi {


    @GET("/api/tasks")
    suspend fun getAllTasks(@Header("Authorization") token: String): Response<GenericData<List<TaskData>>>


    @POST("/api/register")
    suspend fun registerUser(@Body body: RegisterRequestBodyData): Response<GenericData<RegisterReponseData>>

}