package com.example.cryptocurrency.data.model

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CoinPriceInfoRawData {
    @SerializedName("RAW")
    @Expose
    var coinPriceInfoJSONObject: JsonObject? = null
}