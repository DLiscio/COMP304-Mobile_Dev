package com.damien.liscio.di

import androidx.room.Room
import com.damien.liscio.data.StockDatabase
import com.damien.liscio.data.StockRepository
import com.damien.liscio.data.StockRepositoryImpl
import com.damien.liscio.viewmodel.StocksViewModel
import com.damien.liscio.workers.StockSyncWorker
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val appModules = module {
    single<StockRepository> { StockRepositoryImpl(get()) }
    single { Dispatchers.IO }
    single { StocksViewModel(get()) }

    single {
        Room.databaseBuilder(
            androidContext(),
            StockDatabase::class.java,
            "Stock Database"
        ).fallbackToDestructiveMigration().build()
    }

    single { get<StockDatabase>().stockDao() }
    worker { StockSyncWorker(get(), get(), get()) }
}