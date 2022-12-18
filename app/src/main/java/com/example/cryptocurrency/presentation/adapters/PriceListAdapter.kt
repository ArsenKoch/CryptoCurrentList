package com.example.cryptocurrency.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocurrency.BuildConfig
import com.example.cryptocurrency.R
import com.example.cryptocurrency.data.network.model.CoinInfoDto
import com.example.cryptocurrency.utils.PriceDiffUtilsCallback
import com.example.cryptocurrency.utils.getTimeHMSFromTimestamp


class PriceListAdapter(private val context: Context, diffUtilCallBack: PriceDiffUtilsCallback) :
    ListAdapter<CoinInfoDto, PriceListAdapter.CoinPriceViewHolder>(diffUtilCallBack) {

    var onItemClickListener: ((CoinInfoDto) -> Unit)? = null

//    lateinit var binding: ItemCoinsListContentBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinPriceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coins_list_content, parent, false)
        return CoinPriceViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinPriceViewHolder, position: Int) {
        val priceInfo = getItem(position)
        holder.textViewLastUpdated.text =
            String.format(
                context.getString(R.string.last_update_label_with_placeholder),
                getTimeHMSFromTimestamp(priceInfo.lastUpdate?.times(1000) ?: 0)
            )
        holder.textViewPrice.text = priceInfo.price
        holder.textViewSymbols.text =
            String.format(
                context.getString(R.string.text_view_label_symbols),
                priceInfo.fromSymbol,
                priceInfo.toSymbol
            )
        Glide.with(context).load(BuildConfig.BASE_IMAGES_URL + priceInfo.imageUrl)
            .into(holder.imageViewLogoCoins)
    }

    inner class CoinPriceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewSymbols = itemView.text_view_sy mbols
        val textViewPrice = itemView.text_view_price
        val textViewLastUpdated = itemView.text_view_last_update
        val imageViewLogoCoins = itemView.image_view_logo_coin

        init {
            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(getItem(adapterPosition))
                }
            }
        }
    }

}