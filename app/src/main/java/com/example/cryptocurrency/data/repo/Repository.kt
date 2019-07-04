package com.example.cryptocurrency.data.repo

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.example.cryptocurrency.api.ApiFactory
import com.example.cryptocurrency.data.pojo.CoinPriceDisplayInfo
import com.example.cryptocurrency.data.pojo.CoinPriceInfo
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.StringBuilder

class Repository(context: Context) {

    companion object {
        const val TAG = "Repository"
    }

    private val compositeDisposable = CompositeDisposable()

    private val db: AppDatabase = AppDatabase.getInstance(context)
    private val coinPriceInfoDao = db.coinPriceInfoDao()
    private val coinPriceDataToDisplayDao = db.coinPriceDataToDisplayDao()

    val fullPriceList = coinPriceInfoDao.getPriceList()
    val priceListToDisplay = coinPriceDataToDisplayDao.getPriceListToDisplay()

    fun getFullInfoAboutCoin(symbol: String) = coinPriceInfoDao.getFullPriceInfoAboutCoin(symbol)
    fun getInfoAboutCoinToDisplay(symbol: String) = coinPriceDataToDisplayDao.getPriceInfoToDisplayAboutCoin(symbol)

    fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo(20)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                db.coinInfoDao().insertCoins(it.listOfCoins.map { it.coinInfo })
                val symbols = db.coinInfoDao().getAllCoins().map { it.name }
                loadPriceList(symbols)
            },{
                Log.d(TAG, it.message ?: "Unknown error")
            })
        compositeDisposable.add(disposable)
    }

    private fun loadPriceList(symbols: List<String?>) {
        val symbolsBuilder = StringBuilder()
        for (i in 0 until symbols.size) {
            symbolsBuilder.append(symbols[i])
            if (i != symbols.size - 1) {
                symbolsBuilder.append(",")
            }
        }
        val disposable = ApiFactory.apiService.getFullPriceList(symbolsBuilder.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                val listOfDisplayPriceInfo = mutableListOf<CoinPriceDisplayInfo>()
                val coinPriceDisplayInfoJSONObject = it.coinPriceDisplayInfoJSONObject
                coinPriceDisplayInfoJSONObject?.apply {
                    for (key in keySet()) {
                        val infoJsonObject = getAsJsonObject(key)
                        for (currency in infoJsonObject.keySet()) {
                            val priceInfo = Gson().fromJson(infoJsonObject.getAsJsonObject(currency), CoinPriceDisplayInfo::class.java)
                            listOfDisplayPriceInfo.add(priceInfo)
                        }
                    }
                }
                db.coinPriceDataToDisplayDao().insertPriceListToDisplay(listOfDisplayPriceInfo)
                val listOfFullPriceInfo = mutableListOf<CoinPriceInfo>()
                val coinPriceInfoJSONObject = it.coinPriceInfoJSONObject
                coinPriceInfoJSONObject?.apply {
                    for (key in keySet()) {
                        val infoJsonObject = getAsJsonObject(key)
                        for (currency in infoJsonObject.keySet()) {
                            val priceInfo = Gson().fromJson(infoJsonObject.getAsJsonObject(currency), CoinPriceInfo::class.java)
                            listOfFullPriceInfo.add(priceInfo)
                        }
                    }
                }
                db.coinPriceInfoDao().insertFullPriceList(listOfFullPriceInfo)
            }, {
                Log.d(TAG, it.message ?: "Unknown error")
            })
        compositeDisposable.add(disposable)
    }

    fun clearResources() {
        compositeDisposable.dispose()
    }


}