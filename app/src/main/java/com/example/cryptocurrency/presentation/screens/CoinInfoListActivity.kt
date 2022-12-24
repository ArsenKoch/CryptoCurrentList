package com.example.cryptocurrency.presentation.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptocurrency.databinding.CoinsListBinding
import com.example.cryptocurrency.domain.CoinInfo
import com.example.cryptocurrency.presentation.adapters.CoinInfoAdapter
import com.example.cryptocurrency.presentation.viewmodels.CoinInfoViewModel

class CoinInfoListActivity : AppCompatActivity() {

    private lateinit var coinInfoViewModel: CoinInfoViewModel

    private val binding by lazy {
        CoinsListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinInfo: CoinInfo) {
                val intent = coinInfo.fromSymbol?.let {
                    CoinDetailActivity.newIntent(
                        this@CoinInfoListActivity,
                        it
                    )
                }
                startActivity(intent)
            }
        }
        binding.rvCoinPriceList.adapter = adapter
        coinInfoViewModel = ViewModelProvider(this)[CoinInfoViewModel::class.java]
        coinInfoViewModel.coinInfoList.observe(this) {
            adapter.coinInfoList = it
        }
    }
}