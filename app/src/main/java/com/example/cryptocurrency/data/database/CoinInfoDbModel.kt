package com.example.cryptocurrency.data.database

import androidx.room.PrimaryKey


data class CoinInfoDbModel(

    val lastMarket: String?,
    val toSymbol: String?,
    @PrimaryKey
    val fromSymbol: String?,
    val price: String?,
    val lastUpdate: Long?,
    val highDay: String?,
    val lowDay: String?,
    val imageUrl: String?
)