package com.example.cryptocurrency.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cryptocurrency.data.Repository

class CoinPriceViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository(application)

    fun getFullPriceList() = repository.fullPriceList
    fun getFullInfoAboutCoin(symbol: String) = repository.getFullInfoAboutCoin(symbol)
}