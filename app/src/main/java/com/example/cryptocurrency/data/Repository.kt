package com.example.cryptocurrency.data

import android.content.Context
import com.example.cryptocurrency.data.database.AppDatabase

class Repository(context: Context) {

    private val db: AppDatabase = AppDatabase.getInstance(context)
    private val coinPriceInfoDao = db.coinPriceInfoDao()

    val fullPriceList = coinPriceInfoDao.getPriceList()
    fun getPriceInfoAboutCoin(symbol: String) = coinPriceInfoDao.getPriceInfoAboutCoin(symbol)
}