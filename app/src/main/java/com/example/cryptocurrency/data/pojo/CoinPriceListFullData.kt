package com.example.cryptocurrency.data.pojo

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CoinPriceListFullData {
    @SerializedName("RAW")
    @Expose
    var coinPriceInfoJSONObject: JsonObject? = null
    @SerializedName("DISPLAY")
    @Expose
    var coinPriceDisplayInfoJSONObject: JsonObject? = null
}