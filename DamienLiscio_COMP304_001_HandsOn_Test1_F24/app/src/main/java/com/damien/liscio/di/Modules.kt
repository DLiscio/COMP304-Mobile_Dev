package com.damien.liscio.di

import com.damien.liscio.data.ContactRepository
import com.damien.liscio.data.ContactsRepositoryImpl
import com.damien.liscio.viewModel.ContactsViewModel
import org.koin.dsl.module

val appModules = module {
    single<ContactRepository> { ContactsRepositoryImpl() }
    single { ContactsViewModel(get()) }
}