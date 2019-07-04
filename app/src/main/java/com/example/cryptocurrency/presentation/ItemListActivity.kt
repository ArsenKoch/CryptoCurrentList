package com.example.cryptocurrency.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cryptocurrency.R
import com.example.cryptocurrency.data.repo.ServiceOfLoadingData
import com.example.cryptocurrency.presentation.App.Companion.KEY_REFRESHING_PERIOD
import com.example.cryptocurrency.presentation.App.Companion.SHARED_PREFS_NAME

import com.example.cryptocurrency.presentation.adapters.PriceListAdapter
import com.example.cryptocurrency.presentation.utils.PriceDiffUtilsCallback
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import kotlinx.android.synthetic.main.item_list.*
import java.util.prefs.Preferences

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    private var disposable: Disposable? = null

    private lateinit var adapter: PriceListAdapter
    private lateinit var coinPriceViewModel: CoinPriceViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)
        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        coinPriceViewModel = ViewModelProviders.of(this).get(CoinPriceViewModel::class.java)

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }
        setupRecyclerView(item_list)
        setupSeekBar()
        coinPriceViewModel.getFullPriceList().observe(this, Observer {
            adapter.submitList(it)
        })
    }



    private fun setupSeekBar() {
        seek_bar_time_of_refreshing.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                var progress = (0.6 * p1).toInt()
                if (progress > 60) progress = 60
                if (progress < 1) {
                    seek_bar_time_of_refreshing.progress = 1
                    progress = 1
                }
                sharedPreferences.edit().putInt(KEY_REFRESHING_PERIOD, p1).apply()
                text_view_period_of_refreshing_label.text = String.format(getString(R.string.period_of_refreshing_label), progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        seek_bar_time_of_refreshing.progress = if (sharedPreferences.contains(KEY_REFRESHING_PERIOD)) {
            sharedPreferences.getInt(KEY_REFRESHING_PERIOD, 30)
        } else {
            30 //default value of refreshing period (percent from minutes)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = PriceListAdapter(this, PriceDiffUtilsCallback())
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = null
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
