package com.example.cryptocurrency.api

import com.example.cryptocurrency.BuildConfig
import com.example.cryptocurrency.data.pojo.CoinInfoFullData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        private const val QUERY_PARAMS_LIMIT = "limit"
        private const val QUERY_PARAMS_API_KEY = "api_key"
        private const val QUERY_PARAMS_TO_SYMBOL = "tsym"
    }

    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAMS_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAMS_TO_SYMBOL) tsym: String = "USD",
        @Query(QUERY_PARAMS_API_KEY) apiKey: String = BuildConfig.API_KEY
    ): Single<CoinInfoFullData>
}