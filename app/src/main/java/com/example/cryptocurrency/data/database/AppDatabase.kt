package com.example.cryptocurrency.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptocurrency.data.network.model.CoinNameDto
import com.example.cryptocurrency.data.network.model.CoinInfoDto

@Database(entities = [CoinInfoDto::class, CoinNameDto::class], version = 2 , exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        private const val DB_NAME = "main.db"
        private var db: AppDatabase? = null
        private val LOCK = Any()
        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context, AppDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun coinPriceInfoDao(): CoinInfoDao
}