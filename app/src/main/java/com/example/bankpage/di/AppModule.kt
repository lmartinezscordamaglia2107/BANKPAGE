package com.example.bankpage.di

import androidx.room.Room
import com.example.bankpage.data.db.AppDatabase
import com.example.bankpage.data.repository.BankRepository
import com.example.bankpage.ui.BankViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    
    // Configuração do Banco de Dados Room
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "bank_database"
        ).build()
    }

    // DAO
    single { get<AppDatabase>().transactionDao() }

    // Repository
    single { BankRepository(get()) }
    
    // ViewModel com injeção do repository
    viewModel { BankViewModel(get()) }
}
