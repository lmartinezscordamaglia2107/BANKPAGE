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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
fun BANKPAGEApp(
    viewModel: BankViewModel = koinViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestinationRoute = navBackStackEntry?.destination?.route

    val showBottomBar = AppDestinations.entries.any { it.route == currentDestinationRoute && it.showInBottomBar }
    val adaptiveInfo = currentWindowAdaptiveInfo()

    NavigationSuiteScaffold(
        layoutType = if (!showBottomBar) {
            NavigationSuiteType.None
        } else {
            NavigationSuiteType.NavigationBar
        },
        navigationSuiteItems = {
            AppDestinations.entries.filter { it.showInBottomBar }.forEach { destination ->
                item(
                    icon = {
                        Icon(
                            painterResource(destination.icon),
                            contentDescription = destination.label
                        )
                    },
                    label = { Text(destination.label) },
                    selected = currentDestinationRoute == destination.route,
                    onClick = {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = AppDestinations.HOME.route,
                modifier = Modifier.padding(innerPadding),
                enterTransition = {
                    slideInHorizontally(animationSpec = tween(400)) { it } + fadeIn(tween(400))
                },
                exitTransition = {
                    slideOutHorizontally(animationSpec = tween(400)) { -it } + fadeOut(tween(400))
                },
                popEnterTransition = {
                    slideInHorizontally(animationSpec = tween(400)) { -it } + fadeIn(tween(400))
                },
                popExitTransition = {
                    slideOutHorizontally(animationSpec = tween(400)) { it } + fadeOut(tween(400))
                }
            ) {
                composable(AppDestinations.HOME.route) {
                    HomeScreen(
                        balance = viewModel.balance,
                        limit = viewModel.limit,
                        transactions = viewModel.transactions.collectAsState().value,
                        onNavigateToPix = { navController.navigate(AppDestinations.PIX.route) }
                    )
                }
                composable(AppDestinations.FAVORITES.route) {
                    FavoritesScreen(
                        onSendMoney = { contact ->
                            viewModel.addTransaction("Para ${contact.name}", 50.0, true)
                        }
                    )
                }
                composable(AppDestinations.PROFILE.route) {
                    ProfileScreen()
                }
                composable(AppDestinations.PIX.route) {
                    PixScreen(
                        onBack = { navController.popBackStack() },
                        onConfirmPix = { key, value ->
                            viewModel.addTransaction("Pix para $key", value, true)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    balance: Double,
    limit: Double,
    transactions: List<Transaction>,
    onNavigateToPix: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pixLabel = stringResource(R.string.action_pix)
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.welcome_message),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        
        Spacer(modifier = Modifier.height(8.dp))

        BalanceCard(balance = balance, limit = limit)

        Spacer(modifier = Modifier.height(8.dp))

        ActionButtons(onActionClick = { label ->
            if (label == pixLabel) onNavigateToPix()
        })
        
        Spacer(modifier = Modifier.height(24.dp))

        ExpenseCategoryChart()

        Spacer(modifier = Modifier.height(24.dp))

        SavingsGoals()

        Spacer(modifier = Modifier.height(24.dp))
        
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
fun PixScreen(
    onBack: () -> Unit,
    onConfirmPix: (String, Double) -> Unit
) {
    var pixKey by remember { mutableStateOf("") }
    var pixValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
            }
            Text(text = "Área Pix", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Chave Pix", fontWeight = FontWeight.SemiBold)
        TextField(
            value = pixKey,
            onValueChange = { pixKey = it },
            placeholder = { Text("CPF, E-mail, Celular ou Chave Aleatória") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Valor", fontWeight = FontWeight.SemiBold)
        TextField(
            value = pixValue,
            onValueChange = { pixValue = it },
            placeholder = { Text("R$ 0,00") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val value = pixValue.toDoubleOrNull() ?: 0.0
                if (pixKey.isNotEmpty() && value > 0) {
                    onConfirmPix(pixKey, value)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = pixKey.isNotEmpty() && pixValue.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E676))
        ) {
            Text(text = "Transferir Agora", color = Color.Black)
        }
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
    val route: String,
    val label: String,
    val icon: Int,
    val showInBottomBar: Boolean = true
) {
    HOME("home", "Home", R.drawable.ic_home),
    FAVORITES("favorites", "Favorites", R.drawable.ic_favorite),
    PROFILE("profile", "Profile", R.drawable.ic_account_box),
    PIX("pix", "Pix", R.drawable.ic_favorite, showInBottomBar = false),
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
            ),
            onNavigateToPix = {}
        )
    }
}
