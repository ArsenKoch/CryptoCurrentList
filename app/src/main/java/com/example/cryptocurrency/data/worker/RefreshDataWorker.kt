package com.example.cryptocurrency.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.cryptocurrency.data.database.AppDatabase
import com.example.cryptocurrency.data.mapper.CoinMapper
import com.example.cryptocurrency.data.network.ApiFactory
import kotlinx.coroutines.delay

class RefreshDataWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val coinInfoDao = AppDatabase.getInstance(context).coinPriceInfoDao()
    private val mapper = CoinMapper()
    private val apiService = ApiFactory.apiService

    override suspend fun doWork(): Result {
        while (true) {
            val topCoins = apiService.getTopCoinsInfo(limit = 50)
            val fromSyms = mapper.mapNameListToString(topCoins)
            val jsonCont = apiService.getFullPriceList(listOfFromSymbols = fromSyms)
            val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonCont)
            val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
            coinInfoDao.insertPriceList(dbModelList)
            delay(10000)
        }
    }

    companion object {

        const val NAME = "RefreshDataWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }
}