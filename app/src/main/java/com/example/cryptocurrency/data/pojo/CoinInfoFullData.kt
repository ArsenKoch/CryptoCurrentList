package com.example.cryptocurrency.data.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CoinInfoFullData(
    @SerializedName("Data")
    @Expose
    val listOfCoins: List<CoinDatum>
)