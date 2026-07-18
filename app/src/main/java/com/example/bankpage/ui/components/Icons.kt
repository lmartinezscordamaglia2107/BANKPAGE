package com.example.bankpage.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Representa um ícone de ação do banco com um rótulo e o ícone correspondente.
 */
data class BankIcon(
    val label: String,
    val icon: ImageVector
)

/**
 * Objeto utilitário para centralizar os ícones usados no App.
 * Aqui usamos ícones do Material Design.
 */
object BankIcons {
    val Pix = Icons.Default.Add
    val Pagar = Icons.Default.Payments
    val Transferir = Icons.Default.Send
    val Recarga = Icons.Default.Smartphone
    val Emprestimo = Icons.Default.AccountBalanceWallet
}
