package com.example.cryptocurrency.presentation.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptocurrency.R
import com.example.cryptocurrency.data.network.ApiFactory.BASE_IMAGES_URL
import com.example.cryptocurrency.presentation.viewmodels.CoinInfoViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_coin_detail.*

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_coin_detail)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: EMPTY_SYMBOL
        viewModel = ViewModelProvider(this)[CoinInfoViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol).observe(this) {
            text_view_price.text = it.price
            text_view_min_day.text = it.lowDay
            text_view_max_day.text = it.highDay
            text_view_market.text = it.lastMarket
//            text_view_last_update.text = it.lastUpdate
            text_view_from_symbol.text = it.fromSymbol
            text_view_to_symbol.text = it.toSymbol
            Picasso.get().load(BASE_IMAGES_URL + it.imageUrl).into(image_view_logo_coin)
        }
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_SYMBOL = ""

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}
