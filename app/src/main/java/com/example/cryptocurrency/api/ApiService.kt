package com.example.cryptocurrency.api

import com.example.cryptocurrency.BuildConfig
import com.example.cryptocurrency.data.pojo.CoinInfoFullData
import com.example.cryptocurrency.data.pojo.CoinPriceListFullData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        private const val QUERY_PARAMS_LIMIT = "limit"
        private const val QUERY_PARAMS_API_KEY = "api_key"
        private const val QUERY_PARAMS_TO_SYMBOL = "tsym"
        private const val QUERY_PARAMS_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAMS_FROM_SYMBOL = "fsym"
        private const val QUERY_PARAMS_FROM_SYMBOLS = "fsyms"

        private const val CURRENCY_USD = "USD"
        private const val CURRENCY_EUR = "EUR"
        private const val CURRENCY_RUR = "RUR"
    }

    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAMS_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAMS_TO_SYMBOL) tsym: String = CURRENCY_RUR,
        @Query(QUERY_PARAMS_API_KEY) apiKey: String = BuildConfig.API_KEY
    ): Observable<CoinInfoFullData>

    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAMS_FROM_SYMBOLS) listOfFromSymbols: String,
        @Query(QUERY_PARAMS_TO_SYMBOLS) listOfToSymbols: String = CURRENCY_RUR,
        @Query(QUERY_PARAMS_API_KEY) apiKey: String = BuildConfig.API_KEY
    ): Observable<CoinPriceListFullData>
}