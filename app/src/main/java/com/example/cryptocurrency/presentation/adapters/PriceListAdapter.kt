package com.example.cryptocurrency.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrency.R
import com.example.cryptocurrency.data.pojo.CoinPriceInfo
import kotlinx.android.synthetic.main.item_list_content.view.*
import java.util.*
import java.sql.Timestamp
import java.text.SimpleDateFormat


class PriceListAdapter(private val context: Context) : RecyclerView.Adapter<PriceListAdapter.CoinPriceViewHolder>() {

    var priceList: List<CoinPriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinPriceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_content, parent, false)
        return CoinPriceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return priceList.count()
    }

    override fun onBindViewHolder(holder: CoinPriceViewHolder, position: Int) {
        val priceInfo = priceList[position]
        holder.textViewLastUpdated.text =
            String.format(context.getString(R.string.last_update_label), getTimeHMS(priceInfo.lastUpdate ?: 0))
        holder.textViewPrice.text = priceInfo.price
        holder.textViewSymbols.text =
            String.format(context.getString(R.string.text_view_label_symbols), priceInfo.fromSymbol, priceInfo.toSymbol)
    }

    inner class CoinPriceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewSymbols = itemView.text_view_symbols
        val textViewPrice = itemView.text_view_price
        val textViewLastUpdated = itemView.text_view_last_update
    }

    private fun getTimeHMS(timestamp: Long): String {
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

}