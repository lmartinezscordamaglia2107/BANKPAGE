package com.seuusuario.bankpage.data

data class Transaction(
    val id: Int,
    val title: String,
    val value: Double,
    val date: String,
    val isExpense: Boolean // true para saída de dinheiro, false para entrada
)