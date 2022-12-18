package com.example.cryptocurrency.data.mapper

import com.example.cryptocurrency.data.network.model.CoinInfoDto
import com.example.cryptocurrency.data.database.CoinInfoDbModel
import com.example.cryptocurrency.data.network.model.CoinInfoJsonObjectDto
import com.example.cryptocurrency.data.network.model.CoinNamesListDto
import com.example.cryptocurrency.domain.CoinInfo
import com.google.gson.Gson

class CoinMapper {

    fun mapDtoToDbModel(dto: CoinInfoDto): CoinInfoDbModel = CoinInfoDbModel(
        lastMarket = dto.lastMarket,
        toSymbol = dto.toSymbol,
        fromSymbol = dto.fromSymbol,
        price = dto.price,
        lastUpdate = dto.lastUpdate,
        highDay = dto.highDay,
        lowDay = dto.lowDay,
        imageUrl = dto.imageUrl
    )

    fun mapJsonContainerToListCoinInfo(jsonObjectDto: CoinInfoJsonObjectDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val json = jsonObjectDto.json ?: return result
        val coinKeySet = jsonObjectDto.keySet()
        for (coinKey in coinKeySet) {
            val curJson = jsonObjectDto.getAsJsonObj(coinKey)
            val curKeySet = curJson.keySet()
            for (curKey in curKeySet) {
                val priceInfo = Gson().fromJson(
                    curJson.getAsJsonObj(curKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNameListToString(namesListDto: CoinNamesListDto) : String {
        return namesListDto.names?.map {
            it.coinName?.name
        }?.joinTosTring(",") ?: ""
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel):CoinInfo = CoinInfo(
        lastMarket = dbModel.lastMarket,
        toSymbol = dbModel.toSymbol,
        fromSymbol = dbModel.fromSymbol,
        price = dbModel.price,
        lastUpdate = dbModel.lastUpdate,
        highDay = dbModel.highDay,
        lowDay = dbModel.lowDay,
        imageUrl = dbModel.imageUrl
    )
}