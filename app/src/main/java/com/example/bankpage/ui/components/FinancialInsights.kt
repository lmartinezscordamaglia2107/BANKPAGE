package com.example.bankpage.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SavingsGoals(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Metas de Poupança", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(12.dp))
            
            GoalItem(name = "Viagem de Férias", progress = 0.65f, value = "R$ 3.250,00")
            Spacer(modifier = Modifier.height(8.dp))
            GoalItem(name = "Reserva de Emergência", progress = 0.30f, value = "R$ 1.500,00")
        }
    }
}

@Composable
fun GoalItem(name: String, progress: Float, value: String) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = name, fontSize = 14.sp)
            Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(8.dp),
            color = Color(0xFF00E676),
            trackColor = Color.LightGray,
            strokeCap = StrokeCap.Round
        )
    }
}

@Composable
fun ExpenseCategoryChart(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Text(text = "Gastos por Categoria", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(modifier = Modifier.fillMaxWidth().height(20.dp)) {
            Box(modifier = Modifier.weight(0.5f).fillMaxHeight().padding(end = 2.dp).background(Color(0xFF9C27B0), RoundedCornerShape(4.dp)))
            Box(modifier = Modifier.weight(0.3f).fillMaxHeight().padding(end = 2.dp).background(Color(0xFF00BCD4), RoundedCornerShape(4.dp)))
            Box(modifier = Modifier.weight(0.2f).fillMaxHeight().background(Color(0xFFFFEB3B), RoundedCornerShape(4.dp)))
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ChartLegend(name = "Lazer", color = Color(0xFF9C27B0))
            ChartLegend(name = "Comida", color = Color(0xFF00BCD4))
            ChartLegend(name = "Outros", color = Color(0xFFFFEB3B))
        }
    }
}

@Composable
fun ChartLegend(name: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(10.dp).background(color, CircleShape))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = name, fontSize = 12.sp, color = Color.Gray)
    }
}
