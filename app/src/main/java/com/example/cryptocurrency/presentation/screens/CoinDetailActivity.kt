package com.example.cryptocurrency.presentation.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptocurrency.R
import com.example.cryptocurrency.data.network.ApiFactory.BASE_IMAGES_URL
import com.example.cryptocurrency.presentation.viewmodels.CoinInfoViewModel
import com.example.cryptocurrency.utils.getTimeHMSFromTimestamp
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_coin_detail.*

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: EMPTY_SYMBOL
        viewModel = ViewModelProvider(this)[CoinInfoViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol).observe(this) {
            tvPrice.text = it.price
            tvMinPrice.text = it.lowDay
            tvMaxprice.text = it.highDay
            tvLastMarket.text = it.lastMarket
            tvLastUpdate.text = it.lastUpdate?.let { it1 -> getTimeHMSFromTimestamp(it1) }
            tvFromSymbol.text = it.fromSymbol
            tvToSymbol.text = it.toSymbol
            Picasso.get().load(BASE_IMAGES_URL + it.imageUrl).into(image_view_logo_coin)
        }
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_SYMBOL = ""

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.getStringExtra(EXTRA_FROM_SYMBOL)
            return intent
        }
    }
}
