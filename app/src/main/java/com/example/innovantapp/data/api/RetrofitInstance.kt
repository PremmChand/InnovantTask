package com.example.innovantapp.data.api

import ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://klinq.com/rest/V1/"
    private val retrofit by lazy {
        Retrofit.Builder()
            //.baseUrl("https://klinq.com/rest/V1/")
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}