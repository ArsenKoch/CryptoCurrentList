package com.example.cryptocurrency.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CoinNamesListDto(
    @SerializedName("Data")
    @Expose
    val coinNameContainerDtos: List<CoinNameContainerDto>? = null
)