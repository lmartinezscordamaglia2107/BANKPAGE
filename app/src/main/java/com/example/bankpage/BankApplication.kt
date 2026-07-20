package com.example.bankpage

import android.app.Application

/**
 * Classe de Aplicação base. 
 * Necessária para configurar bibliotecas de Injeção de Dependência 
 * e monitoramento de acessibilidade em nível global.
 */
class BankApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Aqui você inicializaria bibliotecas como Hilt, Koin ou Firebase
    }
}
