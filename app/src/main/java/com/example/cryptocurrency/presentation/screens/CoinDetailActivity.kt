package com.example.cryptocurrency.presentation.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.ActivityCoinDetailBinding

class CoinDetailActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCoinDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, CoinDetailFragment())
                .commit()
        }

    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
    }
}