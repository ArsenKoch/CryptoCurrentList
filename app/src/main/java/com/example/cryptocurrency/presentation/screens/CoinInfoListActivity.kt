package com.example.cryptocurrency.presentation.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptocurrency.R
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
                coinInfo.fromSymbol?.let { launchFragment(fromString = it) }
            }
        }
        binding.rvCoinPriceList.adapter = adapter
        binding.rvCoinPriceList.itemAnimator = null
        coinInfoViewModel = ViewModelProvider(this)[CoinInfoViewModel::class.java]
        coinInfoViewModel.coinInfoList.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun launchFragment(fromString: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromString))
            .addToBackStack(null)
            .commit()
    }
}