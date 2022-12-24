package com.example.cryptocurrency.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrency.R
import com.example.cryptocurrency.data.network.ApiFactory.BASE_IMAGES_URL
import com.example.cryptocurrency.domain.CoinInfo
import com.example.cryptocurrency.utils.getTimeHMSFromTimestamp
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_coin_detail.view.ivLogoCoin
import kotlinx.android.synthetic.main.fragment_coin_detail.view.tvLastUpdate
import kotlinx.android.synthetic.main.fragment_coin_detail.view.tvPrice
import kotlinx.android.synthetic.main.item_coins_list_content.view.*


class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var onCoinClickListener: OnCoinClickListener? = null

    var coinInfoList: List<CoinInfo> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun getItemCount() = coinInfoList.size

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder) {
            with(coin) {
                val symbolsTemplate = context.resources.getString(R.string.text_view_label_symbols)
                val lastUpdateTemplate = context.resources.getString(R.string.last_update_label)
                tvSymbols.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                tvVPrice.text = price
                tvLastUpdated.text = String.format(lastUpdateTemplate,
                    lastUpdate?.let { getTimeHMSFromTimestamp(it) })
                Picasso.get().load(BASE_IMAGES_URL + imageUrl).into(ivLogoCoins)
                itemView.setOnClickListener {
                    onCoinClickListener?.onCoinClick(this)
                }
            }
        }
    }

    inner class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSymbols: TextView = itemView.text_view_symbols
        val tvVPrice: TextView = itemView.tvPrice
        val tvLastUpdated: TextView = itemView.tvLastUpdate
        val ivLogoCoins: ImageView = itemView.ivLogoCoin
    }

    interface OnCoinClickListener {

        fun onCoinClick(coinInfo: CoinInfo)
    }
}