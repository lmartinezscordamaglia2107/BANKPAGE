package com.example.bankpage.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankpage.data.Transaction
import com.example.bankpage.data.repository.BankRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BankViewModel(private val repository: BankRepository) : ViewModel() {
    var balance by mutableStateOf(2500.80)
        private set

    var limit by mutableStateOf(10000.00)
        private set

    // Transações agora vêm do banco de dados em tempo real
    val transactions: StateFlow<List<Transaction>> = repository.allTransactions
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addTransaction(title: String, value: Double, isExpense: Boolean) {
        viewModelScope.launch {
            val newTransaction = Transaction(
                id = 0, // Room gerará o ID
                title = title,
                value = value,
                date = "Hoje",
                isExpense = isExpense
            )
            repository.insertTransaction(newTransaction)
            
            // Atualiza o saldo localmente para feedback instantâneo (opcional, ou pode vir do BD também)
            if (isExpense) {
                balance -= value
            } else {
                balance += value
            }
        }
    }
}
