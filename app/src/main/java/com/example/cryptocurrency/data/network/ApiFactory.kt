package com.example.cryptocurrency.data.network

import com.example.cryptocurrency.BuildConfig
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor


object ApiFactory {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()

    init {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    }

    const val API_KEY = "API KEY"


    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .authenticator(object : Authenticator {
            override fun authenticate(route: Route?, response: Response): Request {
                return Request.Builder()
                    .addHeader("authorization", API_KEY).build()
            }
        })
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

}