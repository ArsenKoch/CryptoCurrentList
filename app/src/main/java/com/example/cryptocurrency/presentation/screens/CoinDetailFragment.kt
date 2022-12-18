package com.example.cryptocurrency.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cryptocurrency.R
import com.example.cryptocurrency.data.network.model.CoinInfoDto
import com.example.cryptocurrency.presentation.viewmodels.CoinsInfoViewModel
import com.example.cryptocurrency.utils.getTimeHMSFromTimestamp
import kotlinx.android.synthetic.main.fragment_coin_detail.*

class CoinDetailFragment : Fragment() {

    private lateinit var viewModel: CoinsInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(CoinsInfoViewModel::class.java)
        return inflater.inflate(R.layout.fragment_coin_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                val coinId = it.getString(ARG_ITEM_ID)?:""
                viewModel.getPriceInfoAboutCoin(coinId).observe(viewLifecycleOwner, Observer {
                    showCoinIfoDetails(it)
                })
            }
        }
    }

    private fun showCoinIfoDetails(coinInfoDto: CoinInfoDto) {
        Glide.with(this).load(BuildConfig.BASE_IMAGES_URL + coinInfoDto.imageUrl).into(image_view_logo_coin)
        text_view_from_symbol.text = coinInfoDto.fromSymbol
        text_view_to_symbol.text = coinInfoDto.toSymbol
        text_view_price.text = coinInfoDto.price
        text_view_min_day.text = coinInfoDto.lowDay
        text_view_max_day.text = coinInfoDto.highDay
        text_view_market.text = coinInfoDto.lastMarket
        text_view_last_update.text = getTimeHMSFromTimestamp(coinInfoDto.lastUpdate?.times(1000)?:0)
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }
}
