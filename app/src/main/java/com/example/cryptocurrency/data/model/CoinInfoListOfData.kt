package com.example.cryptocurrency.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CoinInfoListOfData(
    @SerializedName("Data")
    @Expose
    val listOfCoins: List<CoinDatum>
)