package com.example.cryptocurrency.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptocurrency.domain.dao.CoinInfoDao
import com.example.cryptocurrency.domain.dao.CoinPriceInfoDao
import com.example.cryptocurrency.data.pojo.CoinInfo
import com.example.cryptocurrency.data.pojo.CoinPriceInfo

@Database(entities = [CoinPriceInfo::class, CoinInfo::class], version = 2 , exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        private const val DB_NAME = "main.db"
        private var db: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            db?.let { return it }
            val instance = Room.databaseBuilder(context, AppDatabase::class.java,
                DB_NAME
            ).
                fallbackToDestructiveMigration()
                .build()
            db = instance
            return instance
        }
    }

    abstract fun coinPriceInfoDao(): CoinPriceInfoDao
    abstract fun coinInfoDao(): CoinInfoDao
}