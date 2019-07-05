package com.example.cryptocurrency.data.repo

import android.content.Context

class Repository(context: Context) {

    private val db: AppDatabase = AppDatabase.getInstance(context)
    private val coinPriceInfoDao = db.coinPriceInfoDao()
    private val coinPriceDataToDisplayDao = db.coinPriceDataToDisplayDao()

    val fullPriceList = coinPriceInfoDao.getPriceList()
    val priceListToDisplay = coinPriceDataToDisplayDao.getPriceListToDisplay()

    fun getFullInfoAboutCoin(symbol: String) = coinPriceInfoDao.getFullPriceInfoAboutCoin(symbol)
    fun getInfoAboutCoinToDisplay(symbol: String) = coinPriceDataToDisplayDao.getPriceInfoToDisplayAboutCoin(symbol)

}