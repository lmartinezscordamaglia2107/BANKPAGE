package com.example.bankpage

import android.app.Application
import com.example.bankpage.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Classe de Aplicação base para o BANKPAGE.
 * 
 * Agora configurada com Koin (DI) e Timber (Logs).
 */
class BankApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        
        // Inicializa o Timber para logs inteligentes
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        // Inicializa o Koin para Injeção de Dependência
        startKoin {
            androidLogger()
            androidContext(this@BankApplication)
            modules(appModule)
        }

        Timber.d("BANKPAGE inicializado com sucesso com Koin e Timber.")
    }
}
