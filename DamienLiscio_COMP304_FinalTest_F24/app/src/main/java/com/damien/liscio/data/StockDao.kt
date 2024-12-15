package com.damien.liscio.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stockInfo: StockInfo)

    @Update
    suspend fun updateStock(stock: StockInfo)

    @Delete
    suspend fun deleteStock(stock: StockInfo)

    @Query("SELECT * FROM stock_info ORDER BY stockSymbol ASC")
    fun getStocks(): Flow<List<StockInfo>>
}