package com.seuusuario.bankpage.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BalanceCard(balance: Double, limit: Double) {
    // Estado do Compose que controla se o saldo está visível ou oculto
    var isBalanceVisible by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E24)) // Design escuro moderno
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            // Linha superior com o título e o botão do olhinho
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Saldo disponível",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                // Botão interativo para alternar a visibilidade
                IconButton(onClick = { isBalanceVisible = !isBalanceVisible }) {
                    Icon(
                        imageVector = if (isBalanceVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (isBalanceVisible) "Ocultar saldo" else "Exibir saldo",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Texto do saldo que muda dinamicamente com base no estado
            Text(
                text = if (isBalanceVisible) "R$ ${String.format("%.2f", balance)}" else "R$ ••••",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Limite de Crédito (também reage à ocultação para maior privacidade)
            Text(
                text = if (isBalanceVisible) "Limite de Crédito: R$ ${String.format("%.2f", limit)}" else "Limite de Crédito: R$ ••••",
                color = Color(0xFF00E676), // Verde neon para o limite
                fontSize = 14.sp
            )
        }
    }
}