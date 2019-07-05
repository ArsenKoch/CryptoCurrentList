package com.example.cryptocurrency.presentation.adapters

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.cryptocurrency.data.pojo.CoinPriceInfo

class PriceDiffUtilsCallback: DiffUtil.ItemCallback<CoinPriceInfo>() {
    override fun areItemsTheSame(oldItem: CoinPriceInfo, newItem: CoinPriceInfo): Boolean {
        val result = oldItem.fromSymbol == newItem.fromSymbol && oldItem.lastUpdate == newItem.lastUpdate
        Log.d("PriceDiffUtilsCallback", "areItemsTheSame: $result")
        return result
    }

    override fun areContentsTheSame(oldItem: CoinPriceInfo, newItem: CoinPriceInfo): Boolean {
        val result = oldItem == newItem
        Log.d("PriceDiffUtilsCallback", "areContentsTheSame: $result")
        return result
    }
}