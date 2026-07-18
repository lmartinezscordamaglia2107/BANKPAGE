package com.example.bankpage.data

/**
 * Modelo de dados para representar uma transação financeira.
 */
data class Transaction(
    val id: Int,
    val title: String,
    val value: Double,
    val date: String,
    val isExpense: Boolean
)
