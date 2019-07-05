package com.example.cryptocurrency.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cryptocurrency.R
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
                    item_detail.text = it.price
                })
            }
        }
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }
}
