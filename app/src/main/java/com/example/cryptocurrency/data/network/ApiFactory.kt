package com.example.cryptocurrency.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiFactory {

    const val BASE_IMAGES_URL = "https://www.cryptocompare.com"
    private const val BASE_URL = "https://min-api.cryptocompare.com/data/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}