package com.example.bankpage.data

/**
 * Modelo de dados para representar um contato favorito.
 */
data class Contact(
    val id: Int,
    val name: String,
    val bank: String,
    val accountNumber: String,
    val color: Long // Cor hexadecimal para o avatar
)
