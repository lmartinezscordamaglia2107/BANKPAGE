package com.example.bankpage.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankpage.R

@Composable
fun ActionButtons(
    modifier: Modifier = Modifier,
    onActionClick: (String) -> Unit = {}
) {
    val actions = listOf(
        BankIcon(stringResource(R.string.action_pix), BankIcons.Pix),
        BankIcon(stringResource(R.string.action_pay), BankIcons.Pagar),
        BankIcon(stringResource(R.string.action_transfer), BankIcons.Transferir),
        BankIcon(stringResource(R.string.action_recharge), BankIcons.Recarga),
        BankIcon(stringResource(R.string.action_loan), BankIcons.Emprestimo)
    )

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(actions) { action ->
            ActionButtonItem(action, onClick = { onActionClick(action.label) })
        }
    }
}

@Composable
fun ActionButtonItem(action: BankIcon, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(75.dp)
    ) {
        Surface(
            onClick = onClick,
            shape = CircleShape,
            color = Color(0xFFF3F4F6),
            modifier = Modifier.size(60.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = action.icon,
                    contentDescription = action.label,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = action.label,
            fontSize = 12.sp,
            color = Color.DarkGray,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ActionButtonsPreview() {
    ActionButtons()
}
