package com.example.cryptocurrency.domain


data class CoinInfo(

    val lastMarket: String?,
    val toSymbol: String?,
    val fromSymbol: String?,
    val price: String?,
    val lastUpdate: String?,
    val highDay: String?,
    val lowDay: String?,
    val imageUrl: String
)
