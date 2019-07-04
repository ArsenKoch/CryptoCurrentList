package com.example.cryptocurrency.api

import com.example.cryptocurrency.BuildConfig
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


object ApiFactory {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()

    init {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    }


    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build();

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

}