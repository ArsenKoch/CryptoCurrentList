package com.example.cryptocurrency.data.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptocurrency.data.dao.CoinInfoDao
import com.example.cryptocurrency.data.dao.CoinPriceDataToDisplayDao
import com.example.cryptocurrency.data.dao.CoinPriceFullDataDao
import com.example.cryptocurrency.data.pojo.CoinInfo
import com.example.cryptocurrency.data.pojo.CoinPriceDisplayInfo
import com.example.cryptocurrency.data.pojo.CoinPriceInfo

@Database(entities = [CoinPriceInfo::class, CoinPriceDisplayInfo::class, CoinInfo::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        private const val DB_NAME = "main.db"
        private var db: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            db?.let { return it }
            val instance = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).
                fallbackToDestructiveMigration()
                .build()
            db = instance
            return instance
        }
    }

    abstract fun coinPriceDataToDisplayDao(): CoinPriceDataToDisplayDao
    abstract fun coinPriceInfoDao(): CoinPriceFullDataDao
    abstract fun coinInfoDao(): CoinInfoDao
}