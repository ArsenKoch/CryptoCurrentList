package com.example.cryptocurrency.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.cryptocurrency.BuildConfig
import com.example.cryptocurrency.R
import com.example.cryptocurrency.data.pojo.CoinPriceInfo
import com.example.cryptocurrency.presentation.utils.getTimeHMSFromTimestamp
import kotlinx.android.synthetic.main.item_detail.*

class ItemDetailFragment : Fragment() {

    private lateinit var viewModel: CoinPriceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(CoinPriceViewModel::class.java)
        return inflater.inflate(R.layout.item_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                val coinId = it.getString(ARG_ITEM_ID)?:""
                viewModel.getFullInfoAboutCoin(coinId).observe(this, Observer {
                    showCoinIfoDetails(it)
                })
            }
        }
    }

    private fun showCoinIfoDetails(coinPriceInfo: CoinPriceInfo) {
        Glide.with(this).load(BuildConfig.BASE_IMAGES_URL + coinPriceInfo.imageUrl).into(image_view_logo_coin)
        text_view_from_symbol.text = coinPriceInfo.fromSymbol
        text_view_to_symbol.text = coinPriceInfo.toSymbol
        text_view_price.text = coinPriceInfo.price
        text_view_min_day.text = coinPriceInfo.lowDay
        text_view_max_day.text = coinPriceInfo.highDay
        text_view_market.text = coinPriceInfo.lastMarket
        text_view_last_update.text = getTimeHMSFromTimestamp(coinPriceInfo.lastUpdate?.times(1000)?:0)
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }
}
