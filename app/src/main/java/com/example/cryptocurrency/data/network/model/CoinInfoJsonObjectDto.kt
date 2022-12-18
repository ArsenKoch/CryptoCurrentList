package com.example.cryptocurrency.data.network.model

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CoinInfoJsonObjectDto {
    @SerializedName("RAW")
    @Expose
    var json: JsonObject? = null
}