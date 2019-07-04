package com.example.cryptocurrency.data.repo

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import com.example.cryptocurrency.api.ApiFactory
import com.example.cryptocurrency.data.dao.CoinPriceDataToDisplayDao
import com.example.cryptocurrency.data.dao.CoinPriceFullDataDao
import com.example.cryptocurrency.data.pojo.CoinPriceDisplayInfo
import com.example.cryptocurrency.data.pojo.CoinPriceInfo
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class Repository(application: Application) {

    companion object {
        const val TAG = "Repository"
    }

    private val compositeDisposable = CompositeDisposable()

    private val db: AppDatabase = AppDatabase.getInstance(application)
    private val coinPriceInfoDao = db.coinPriceInfoDao()
    private val coinPriceDataToDisplayDao = db.coinPriceDataToDisplayDao()

    val fullPriceList = coinPriceInfoDao.getPriceList()
    val priceListToDisplay = coinPriceDataToDisplayDao.getPriceListToDisplay()

    fun getFullInfoAboutCoin(symbol: String) = coinPriceInfoDao.getFullPriceInfoAboutCoin(symbol)
    fun getInfoAboutCoinToDisplay(symbol: String) = coinPriceDataToDisplayDao.getPriceInfoToDisplayAboutCoin(symbol)

    private fun insertFullPriceList(priceList: List<CoinPriceInfo>) {
        InsertFullPriceListTask(coinPriceInfoDao).execute(priceList)
    }

    private class InsertFullPriceListTask(private val coinPriceInfoDao: CoinPriceFullDataDao): AsyncTask<List<CoinPriceInfo>, Unit, Unit>() {
        override fun doInBackground(vararg lists: List<CoinPriceInfo>?) {
            for (list in lists) {
                list?.let {
                    coinPriceInfoDao.insertFullPriceList(it)
                }
            }
        }
    }

    private fun insertPriceListToDisplay(priceList: List<CoinPriceDisplayInfo>) {
        InsertPriceListToDisplayTask(coinPriceDataToDisplayDao).execute(priceList)
    }

    private class InsertPriceListToDisplayTask(private val coinPriceDataToDisplayDao: CoinPriceDataToDisplayDao): AsyncTask<List<CoinPriceDisplayInfo>, Unit, Unit>() {
        override fun doInBackground(vararg lists: List<CoinPriceDisplayInfo>?) {
            for (list in lists) {
                list?.let {
                    coinPriceDataToDisplayDao.insertPriceListToDisplay(it)
                }
            }
        }
    }

    fun loadData() {
        val disposable = ApiFactory.apiService.getFullPriceList("BTC,ETH")
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