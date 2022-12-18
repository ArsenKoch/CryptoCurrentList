package com.example.cryptocurrency.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cryptocurrency.data.database.AppDatabase
import com.example.cryptocurrency.data.mapper.CoinMapper
import com.example.cryptocurrency.data.network.ApiFactory
import com.example.cryptocurrency.domain.CoinInfo
import com.example.cryptocurrency.domain.repository.CoinRepository
import kotlinx.coroutines.delay

class CoinRepositoryImpl(
    application: Application
) : CoinRepository {

    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()

    private val mapper = CoinMapper()

    private val apiService = ApiFactory.apiService


    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(
            coinInfoDao.getPriceList()
        ) { it ->
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return Transformations.map(
            coinInfoDao.getPriceInfoAboutCoin(fromSymbol)
        ) {
            mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while (true) {
            val topCoins = apiService.getTopCoinsInfo(limit = 50)
            val fromSyms = mapper.mapNameListToString(topCoins)
            val jsonCont = apiService.getFullPriceList(listOfFromSymbols = fromSyms)
            val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonCont)
            val dbModelList = coinInfoDtoList.map {
                mapper.mapDtoToDbModel(it)
            }
            coinInfoDao.insertPriceList(dbModelList)
            delay(10000)
        }
    }

}