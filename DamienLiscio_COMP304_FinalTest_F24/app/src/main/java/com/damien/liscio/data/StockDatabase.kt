package com.damien.liscio.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [StockInfo::class],
    version = 7
)
abstract class StockDatabase: RoomDatabase() {
    abstract fun stockDao(): StockDao
}