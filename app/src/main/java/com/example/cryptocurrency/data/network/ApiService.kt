package com.example.cryptocurrency.data.network

import com.example.cryptocurrency.data.network.model.CoinInfoJsonObjectDto
import com.example.cryptocurrency.data.network.model.CoinNamesListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    suspend fun getTopCoinsInfo(
        @Query(QUERY_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAMS_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAMS_TO_SYMBOL) tsym: String = CURRENCY_USD
    ): CoinNamesListDto

    @GET("pricemultifull")
    suspend fun getFullPriceList(
        @Query(QUERY_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAMS_FROM_SYMBOLS) listOfFromSymbols: String,
        @Query(QUERY_PARAMS_TO_SYMBOLS) listOfToSymbols: String = CURRENCY_USD
    ): CoinInfoJsonObjectDto

    companion object {
        private const val QUERY_PARAMS_LIMIT = "limit"
        private const val QUERY_API_KEY = "api_key"
        private const val QUERY_PARAMS_TO_SYMBOL = "tsym"
        private const val QUERY_PARAMS_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAMS_FROM_SYMBOLS = "fsyms"

        private const val CURRENCY_USD = "USD"
    }
}