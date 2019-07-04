package com.example.cryptocurrency.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cryptocurrency.data.repo.Repository

class CoinPriceViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository(application)

    fun getFullPriceList() = repository.fullPriceList
    fun getPriceListToDisplay() = repository.priceListToDisplay
    fun getFullInfoAboutCoin(symbol: String) = repository.getFullInfoAboutCoin(symbol)
    fun getInfoAboutCoinToDisplay(symbol: String) = repository.getInfoAboutCoinToDisplay(symbol)

    //TODO: remove this method
    fun loadData() {
        repository.loadData()
    }

    override fun onCleared() {
        repository.clearResources()
        super.onCleared()
    }
}