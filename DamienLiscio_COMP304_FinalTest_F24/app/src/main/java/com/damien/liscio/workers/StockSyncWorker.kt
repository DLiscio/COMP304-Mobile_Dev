package com.damien.liscio.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.damien.liscio.data.StockRepository


class StockSyncWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val stockRepository: StockRepository
): CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try{
            stockRepository.getStocks()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}