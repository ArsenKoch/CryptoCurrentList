package com.example.cryptocurrency.domain.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptocurrency.data.pojo.CoinPriceInfo

@Dao
interface CoinPriceFullDataDao {
    @Query("SELECT * FROM full_price_list ORDER BY lastUpdate DESC")
    fun getPriceList(): LiveData<List<CoinPriceInfo>>

    @Query("SELECT * FROM full_price_list WHERE fromSymbol == :symbol")
    fun getFullPriceInfoAboutCoin(symbol: String): LiveData<CoinPriceInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFullPriceList(priceList: List<CoinPriceInfo>)
}