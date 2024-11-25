package com.damien.damienliscio_comp304lab3_ex1.di

import com.damien.damienliscio_comp304lab3_ex1.data.SavedLocationRepository
import com.damien.damienliscio_comp304lab3_ex1.data.SavedLocationRepositoryImpl
import com.damien.damienliscio_comp304lab3_ex1.data.WeatherAPI
import com.damien.damienliscio_comp304lab3_ex1.data.WeatherRepository
import com.damien.damienliscio_comp304lab3_ex1.viewModel.SavedLocationsViewModel
import com.damien.damienliscio_comp304lab3_ex1.viewModel.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit

val appModules = module {
    single<SavedLocationRepository> { SavedLocationRepositoryImpl() }
    single { SavedLocationsViewModel(get()) }
    single { WeatherRepository(get()) }
    single { WeatherViewModel(get()) }

    single { get<Retrofit>().create(WeatherAPI::class.java) }
    single { Dispatchers.IO }

}