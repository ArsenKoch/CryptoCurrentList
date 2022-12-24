package com.example.cryptocurrency.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.ItemCoinInfoBinding
import com.example.cryptocurrency.domain.CoinInfo
import com.squareup.picasso.Picasso


class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoViewHolder>() {

    var onCoinClickListener: OnCoinClickListener? = null

    var coinInfoList: List<CoinInfo> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding =
            ItemCoinInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinInfoViewHolder(binding)
    }

    override fun getItemCount() = coinInfoList.size

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
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