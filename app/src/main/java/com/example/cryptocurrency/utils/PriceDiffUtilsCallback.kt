package com.example.cryptocurrency.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.cryptocurrency.data.network.model.CoinInfoDto

class PriceDiffUtilsCallback: DiffUtil.ItemCallback<CoinInfoDto>() {
    override fun areItemsTheSame(oldItem: CoinInfoDto, newItem: CoinInfoDto): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol && oldItem.lastUpdate == newItem.lastUpdate
    }

    override fun areContentsTheSame(oldItem: CoinInfoDto, newItem: CoinInfoDto): Boolean {
        return oldItem == newItem
    }
}