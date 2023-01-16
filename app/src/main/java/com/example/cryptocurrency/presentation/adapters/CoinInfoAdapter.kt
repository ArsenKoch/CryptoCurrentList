package com.example.cryptocurrency.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.ItemCoinInfoBinding
import com.example.cryptocurrency.domain.CoinInfo
import com.squareup.picasso.Picasso


class CoinInfoAdapter(private val context: Context) :
    ListAdapter<CoinInfo, CoinInfoViewHolder>(CoinInfoDiffCallback()) {

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding =
            ItemCoinInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)
        with(holder) {
            with(coin) {
                val symbolsTemplate = context.resources.getString(R.string.text_view_label_symbols)
                val lastUpdateTemplate = context.resources.getString(R.string.last_update_label)
                binding.tvSymbols.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                binding.tvPrice.text = price
                binding.tvLastUpdate.text = String.format(lastUpdateTemplate, lastUpdate)
                Picasso.get().load(imageUrl).into(binding.ivLogoCoin)
                itemView.setOnClickListener {
                    onCoinClickListener?.onCoinClick(this)
                }
            }
        }
    }

    interface OnCoinClickListener {

        fun onCoinClick(coinInfo: CoinInfo)
    }
}