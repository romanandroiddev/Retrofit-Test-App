package com.example.retrofitapp.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {


    fun getInstance(): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )

        val todoInterceptor = TodoInterceptor()

        val client = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).addInterceptor(todoInterceptor).build()

        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://todo.paydali.uz").client(client).build()
        return retrofit
    }


}