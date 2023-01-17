package com.example.cryptocurrency.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cryptocurrency.data.repository.CoinRepositoryImpl
import com.example.cryptocurrency.domain.usecase.GetCoinInfoListUseCase
import com.example.cryptocurrency.domain.usecase.GetCoinInfoUseCase
import com.example.cryptocurrency.domain.usecase.LoadDataUseCase

class CoinInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImpl(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fromSym: String) = getCoinInfoUseCase(fromSym)

    init {
        loadDataUseCase()
    }
}