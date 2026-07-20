package com.example.bankpage.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bankpage.data.Transaction

class BankViewModel : ViewModel() {
    var balance by mutableStateOf(2500.80)
        private set

    var limit by mutableStateOf(10000.00)
        private set

    private val _transactions = mutableStateListOf(
        Transaction(1, "Supermercado", 150.50, "Hoje, 10:30", true),
        Transaction(2, "Salário", 3500.00, "Ontem, 08:00", false),
        Transaction(3, "Posto Shell", 200.00, "15 Jul, 18:20", true),
        Transaction(4, "Transferência Recebida", 50.00, "14 Jul, 12:00", false)
    )
    val transactions: List<Transaction> get() = _transactions

    fun addTransaction(title: String, value: Double, isExpense: Boolean) {
        val newId = (_transactions.maxOfOrNull { it.id } ?: 0) + 1
        val date = "Agora" // Simples para exemplo
        _transactions.add(0, Transaction(newId, title, value, date, isExpense))
        
        if (isExpense) {
            balance -= value
        } else {
            balance += value
        }
    }
}
