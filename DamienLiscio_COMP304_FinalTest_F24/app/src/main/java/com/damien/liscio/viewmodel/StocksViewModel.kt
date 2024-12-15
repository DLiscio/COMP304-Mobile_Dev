package com.damien.liscio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damien.liscio.data.StockInfo
import com.damien.liscio.data.StockRepository
import kotlinx.coroutines.launch

class StocksViewModel(
    private val stockRepository: StockRepository
): ViewModel() {
    private val _stocks = MutableLiveData<List<StockInfo>>(emptyList())
    val stockInfo: LiveData<List<StockInfo>> get() = _stocks

    init {
        getStocks()
    }

    private fun getStocks() {
        viewModelScope.launch {
            val stocks = stockRepository.getStocks().collect { stocks ->
                _stocks.postValue(stocks)
            }
        }
    }

    fun addStock(stockInfo: StockInfo) {
        viewModelScope.launch {
            stockRepository.addStock(stockInfo)
            getStocks()
        }
    }

    fun updateStock(stockInfo: StockInfo) {
        viewModelScope.launch {
            stockRepository.updateStock(stockInfo)
            getStocks()
        }
    }

    fun deleteStock(stockInfo: StockInfo) {
        viewModelScope.launch {
            stockRepository.deleteStock(stockInfo)
            getStocks()
        }
    }
}