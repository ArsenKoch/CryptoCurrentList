package com.example.cryptocurrency.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrency.R
import com.example.cryptocurrency.domain.CoinInfo
import kotlinx.android.synthetic.main.fragment_coin_detail.view.image_view_logo_coin
import kotlinx.android.synthetic.main.fragment_coin_detail.view.text_view_last_update
import kotlinx.android.synthetic.main.fragment_coin_detail.view.text_view_price
import kotlinx.android.synthetic.main.item_coins_list_content.view.*


class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var onCoinClickListener: OnCoinClickListener? = null

    private var coinInfoList: List<CoinInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coins_list_content, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun getItemCount() = coinInfoList.size

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder) {
            with(coin) {
                val symbolsTemplate = context.resources.getString(R.string.text_view_label_symbols)
            }
        }
    }

    inner class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewSymbols: TextView = itemView.text_view_symbols
        val textViewPrice: TextView = itemView.text_view_price
        val textViewLastUpdated: TextView = itemView.text_view_last_update
        val imageViewLogoCoins: ImageView = itemView.image_view_logo_coin
    }

    interface OnCoinClickListener {

        fun onCoinClick(coinInfo: CoinInfo)
    }
}