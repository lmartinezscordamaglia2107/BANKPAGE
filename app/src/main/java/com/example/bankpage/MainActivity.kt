package com.example.bankpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import com.example.bankpage.data.Transaction
import com.example.bankpage.ui.components.ActionButtons
import com.example.bankpage.ui.components.BalanceCard
import com.example.bankpage.ui.components.BankIcons
import com.example.bankpage.ui.components.TransactionList
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
fun BANKPAGEApp() {
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
                AppDestinations.HOME -> HomeScreen(modifier = Modifier.padding(innerPadding))
                AppDestinations.FAVORITES -> FavoritesScreen(modifier = Modifier.padding(innerPadding))
                AppDestinations.PROFILE -> ProfileScreen(modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val transactions = listOf(
        Transaction(1, "Supermercado", 150.50, "Hoje, 10:30", true),
        Transaction(2, "Salário", 3500.00, "Ontem, 08:00", false),
        Transaction(3, "Posto Shell", 200.00, "15 Jul, 18:20", true),
        Transaction(4, "Transferência Recebida", 50.00, "14 Jul, 12:00", false)
    )

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
            balance = 2500.80,
            limit = 10000.00
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Botões de Ação
        ActionButtons()
        
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
fun FavoritesScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Seus contatos favoritos aparecerão aqui", fontSize = 16.sp)
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
fun App() {
    MaterialTheme {
        var timeAtLocation by remember { mutableStateOf("No location selected") }

        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
        ) {
            Text(timeAtLocation)
            Button(onClick = { timeAtLocation = "13:30" }) {
                Text("Show Time At Location")
            }
        }
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
        HomeScreen()
    }
}
