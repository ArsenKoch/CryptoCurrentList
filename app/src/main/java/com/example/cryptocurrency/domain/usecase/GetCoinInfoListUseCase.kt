package com.example.cryptocurrency.domain.usecase

import com.example.cryptocurrency.domain.repository.CoinRepository

class GetCoinInfoListUseCase(
    private val repository: CoinRepository
) {

    operator fun invoke() = repository.getCoinInfoList()
}