package com.example.cryptocurrency.data.mapper

import com.example.cryptocurrency.data.database.CoinInfoDbModel
import com.example.cryptocurrency.data.network.model.CoinInfoDto
import com.example.cryptocurrency.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptocurrency.data.network.model.CoinNamesListDto
import com.example.cryptocurrency.domain.CoinInfo
import com.google.gson.Gson
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class CoinMapper {

    fun mapDtoToDbModel(dto: CoinInfoDbModel): CoinInfoDbModel = CoinInfoDbModel(
        lastMarket = dto.lastMarket,
        toSymbol = dto.toSymbol,
        fromSymbol = dto.fromSymbol,
        price = dto.price,
        lastUpdate = dto.lastUpdate,
        highDay = dto.highDay,
        lowDay = dto.lowDay,
        imageUrl = BASE_IMAGES_URL + dto.imageUrl
    )

    fun mapJsonContainerToListCoinInfo(jsonContainerDto: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val json = jsonContainerDto.json ?: return result
        val coinKeySet = json.keySet()
        for (coinKey in coinKeySet) {
            val curJson = json.getAsJsonObject(coinKey)
            val curKeySet = curJson.keySet()
            for (curKey in curKeySet) {
                val priceInfo = Gson().fromJson(
                    curJson.getAsJsonObject(curKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNameListToString(namesListDto: CoinNamesListDto): String {
        return namesListDto.names?.map {
            it.coinName?.name
        }?.joinToString(",") ?: ""
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel): CoinInfo = CoinInfo(
        lastMarket = dbModel.lastMarket,
        toSymbol = dbModel.toSymbol,
        fromSymbol = dbModel.fromSymbol,
        price = dbModel.price,
        lastUpdate = convertTimestampToTime(dbModel.lastUpdate),
        highDay = dbModel.highDay,
        lowDay = dbModel.lowDay,
        imageUrl = dbModel.imageUrl
    )

    private fun convertTimestampToTime(timestamp: Long?): String? {
        if (timestamp == null) return null
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm::ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object {

        const val BASE_IMAGES_URL = "https://www.cryptocompare.com"
    }
}