package com.example.cryptocurrency.domain.repository

import androidx.lifecycle.LiveData
import com.example.cryptocurrency.domain.CoinInfo

interface CoinRepository {

    fun getCoinInfoList(): LiveData<List<CoinInfo>>

    fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo>

    fun loadData()
}