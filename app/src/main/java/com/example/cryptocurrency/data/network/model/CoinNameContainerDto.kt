package com.example.cryptocurrency.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CoinNameContainerDto (
    @SerializedName("CoinName")
    @Expose
    val coinNameDto: CoinNameDto
)