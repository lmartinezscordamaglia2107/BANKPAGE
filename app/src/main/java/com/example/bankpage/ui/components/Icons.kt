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
 */
object BankIcons {
    val Pix: ImageVector = Icons.Default.Add
    val Pagar: ImageVector = Icons.Default.Payments
    val Transferir: ImageVector = Icons.Default.Send
    val Recarga: ImageVector = Icons.Default.Smartphone
    val Emprestimo: ImageVector = Icons.Default.AccountBalanceWallet
}
