package com.damien.liscio.data

import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getStocks(): Flow<List<StockInfo>>
    suspend fun addStock(stockInfo: StockInfo)
    suspend fun updateStock(stockInfo: StockInfo)
    suspend fun deleteStock(stockInfo: StockInfo)
}