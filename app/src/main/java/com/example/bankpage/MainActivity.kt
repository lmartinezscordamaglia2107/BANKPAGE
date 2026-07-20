package com.example.bankpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bankpage.data.Contact
import com.example.bankpage.data.Transaction
import com.example.bankpage.ui.BankViewModel
import com.example.bankpage.ui.components.*
import com.example.bankpage.ui.theme.BANKPAGETheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BANKPAGETheme {
                BANKPAGEApp()
            }
        }
    }
}

@Composable
fun BANKPAGEApp(viewModel: BankViewModel = viewModel()) {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            painterResource(it.icon),
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            when (currentDestination) {
                AppDestinations.HOME -> HomeScreen(
                    balance = viewModel.balance,
                    limit = viewModel.limit,
                    transactions = viewModel.transactions,
                    modifier = Modifier.padding(innerPadding)
                )
                AppDestinations.FAVORITES -> FavoritesScreen(
                    onSendMoney = { contact ->
                        viewModel.addTransaction("Para ${contact.name}", 50.0, true)
                    },
                    modifier = Modifier.padding(innerPadding)
                )
                AppDestinations.PROFILE -> ProfileScreen(modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

@Composable
fun HomeScreen(
    balance: Double,
    limit: Double,
    transactions: List<Transaction>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        // Mensagem de Boas-vindas
        Text(
            text = stringResource(R.string.welcome_message),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        
        Spacer(modifier = Modifier.height(8.dp))

        // Cartão de Saldo
        BalanceCard(
            balance = balance,
            limit = limit
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Botões de Ação
        ActionButtons()
        
        Spacer(modifier = Modifier.height(24.dp))

        // Gráfico de Gastos (NOVO)
        ExpenseCategoryChart()

        Spacer(modifier = Modifier.height(24.dp))

        // Metas de Poupança (NOVO)
        SavingsGoals()

        Spacer(modifier = Modifier.height(24.dp))
        
        // Atividades recentes
        Text(
            text = stringResource(R.string.label_recent_activities),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TransactionList(transactions = transactions)
    }
}

@Composable
fun FavoritesScreen(onSendMoney: (Contact) -> Unit, modifier: Modifier = Modifier) {
    val favorites = listOf(
        Contact(1, "Ana Oliveira", "BankPage", "1234-5", 0xFFFFE082),
        Contact(2, "Bruno Costa", "Nubank", "9876-0", 0xFFE1BEE7),
        Contact(3, "Carla Souza", "Itaú", "5544-3", 0xFFC8E6C9),
        Contact(4, "Daniel Lima", "Santander", "1122-9", 0xFFB3E5FC)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Seus favoritos",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(favorites) { contact ->
                ContactItem(contact, onSendMoney = { onSendMoney(contact) })
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact, onSendMoney: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF9FAFB), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar circular com inicial
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(contact.color)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = contact.name.take(1),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = contact.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(text = "${contact.bank} • ${contact.accountNumber}", fontSize = 12.sp, color = Color.Gray)
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        IconButton(onClick = onSendMoney) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Enviar dinheiro",
                tint = Color(0xFF1E1E24)
            )
        }
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = BankIcons.Emprestimo,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(text = "Thiago Silva", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(text = "thiago@example.com", fontSize = 14.sp, color = Color.Gray)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = { /* Logout */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sair da conta", color = Color.White)
        }
    }
}

@Composable
@Preview
fun AppPreview() {
    BANKPAGETheme {
        BANKPAGEApp()
    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    HOME("Home", R.drawable.ic_home),
    FAVORITES("Favorites", R.drawable.ic_favorite),
    PROFILE("Profile", R.drawable.ic_account_box),
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BANKPAGETheme {
        HomeScreen(
            balance = 2500.80,
            limit = 10000.00,
            transactions = listOf(
                Transaction(1, "Compra Teste", 50.0, "Agora", true)
            )
        )
    }
}
