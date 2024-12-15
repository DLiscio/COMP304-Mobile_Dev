package com.damien.liscio.data

import kotlinx.coroutines.flow.Flow

class StockRepositoryImpl(
    private val stockDao: StockDao
): StockRepository {

    override suspend fun addStock(stockInfo: StockInfo) {
        stockDao.insert(stockInfo)
    }

    override suspend fun updateStock(stockInfo: StockInfo) {
        stockDao.updateStock(stockInfo)
    }

    override suspend fun deleteStock(stockInfo: StockInfo) {
        stockDao.deleteStock(stockInfo)
    }

    override suspend fun getStocks(): Flow<List<StockInfo>> {
        return stockDao.getStocks()
    }
}

