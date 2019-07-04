package com.example.cryptocurrency.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptocurrency.data.pojo.CoinPriceDisplayInfo

@Dao
interface CoinPriceDataToDisplayDao {
    @Query("SELECT * FROM price_list_to_display ORDER BY lastUpdate DESC")
    fun getPriceListToDisplay(): LiveData<List<CoinPriceDisplayInfo>>

    @Query("SELECT * FROM price_list_to_display WHERE fromSymbol == :symbol")
    fun getPriceInfoToDisplayAboutCoin(symbol: String): LiveData<CoinPriceDisplayInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPriceListToDisplay(priceList: List<CoinPriceDisplayInfo>)
}