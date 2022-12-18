package com.example.cryptocurrency.domain.usecase

import com.example.cryptocurrency.domain.repository.CoinRepository

class LoadDataUseCase(
    private val repository: CoinRepository
) {

    suspend operator fun invoke() = repository.loadData()
}