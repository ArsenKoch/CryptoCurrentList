package com.example.cryptocurrency.data.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CoinDatum (
    @SerializedName("CoinInfo")
    @Expose
    val coinInfo: CoinInfo
)