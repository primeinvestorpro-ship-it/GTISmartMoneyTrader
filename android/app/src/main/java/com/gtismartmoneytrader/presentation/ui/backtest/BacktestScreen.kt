package com.gtismartmoneytrader.presentation.ui.backtest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gtismartmoneytrader.domain.model.*
import com.gtismartmoneytrader.presentation.ui.theme.GTIColors
import com.gtismartmoneytrader.presentation.viewmodel.BacktestViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BacktestScreen(
    viewModel: BacktestViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Backtesting Engine 🧪", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
        Text("Historical strategy performance", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

        // Symbol selector
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Symbol.entries.forEach { symbol ->
                FilterChip(
                    selected = symbol == uiState.selectedSymbol,
                    onClick = { viewModel.selectSymbol(symbol) },
                    label = { Text(symbol.displayName) }
                )
            }
        }

        // Period selector
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BacktestPeriod.entries.forEach { period ->
                FilterChip(
                    selected = period == uiState.selectedPeriod,
                    onClick = { viewModel.selectPeriod(period) },
                    label = { Text(period.label) }
                )
            }
        }

        if (uiState.isLoading) {
            LoadingCard()
        } else if (uiState.result != null) {
            val result = uiState.result!!

            // Summary stats row
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AccuracyStatCard(
                    label = "GTI Accuracy",
                    value = "${String.format("%.1f", result.gtiAccuracyPct)}%",
                    subLabel = "${result.gtiProfitTrades}/${result.totalGtiTrades} trades hit target",
                    color = GTIColors.BuyCall,
                    modifier = Modifier.weight(1f)
                )
                AccuracyStatCard(
                    label = "Straddle Win",
                    value = "${String.format("%.1f", result.straddleWinRatePct)}%",
                    subLabel = "${result.straddleWinTrades}/${result.totalStraddleTrades} days profitable",
                    color = GTIColors.StraddleActive,
                    modifier = Modifier.weight(1f)
                )
            }

            // Combined performance card
            CombinedPerformanceCard(result)

            // GTI performance detail
            PerformanceDetailCard(
                title = "🧠 GTI Engine Performance",
                accuracy = result.gtiAccuracyPct,
                hit = result.gtiProfitTrades,
                total = result.totalGtiTrades,
                accentColor = GTIColors.BuyCall
            )

            // Straddle performance detail
            PerformanceDetailCard(
                title = "📊 Straddle Engine Performance",
                accuracy = result.straddleWinRatePct,
                hit = result.straddleWinTrades,
                total = result.totalStraddleTrades,
                accentColor = GTIColors.StraddleActive
            )

            BacktestDisclaimer()
        } else {
            NoDataCard()
        }
    }
}

@Composable
private fun AccuracyStatCard(
    label: String, value: String, subLabel: String, color: Color, modifier: Modifier = Modifier
) {
    Card(modifier = modifier, shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = color)
            Text(subLabel, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun CombinedPerformanceCard(result: BacktestResult) {
    val isPositive = result.combinedReturnPct >= 60.0
    val color = if (isPositive) GTIColors.BuyCall else GTIColors.ConfidenceMedium

    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Combined Strategy Score", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    "${String.format("%.1f", result.combinedReturnPct)}%",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = color
                )
                Column {
                    Text("${result.period.label} period", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(result.symbol.displayName, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = (result.combinedReturnPct / 100f).toFloat(),
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                color = color,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}

@Composable
private fun PerformanceDetailCard(
    title: String, accuracy: Double, hit: Int, total: Int, accentColor: Color
) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Win Rate", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("${String.format("%.1f", accuracy)}%", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = accentColor)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("Trades", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("$hit / $total", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = if (total > 0) (hit.toFloat() / total) else 0f,
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                color = accentColor,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}

@Composable
private fun LoadingCard() {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp)) {
        Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(12.dp))
                Text("Running backtest…", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun NoDataCard() {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp)) {
        Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
            Text("Insufficient historical data for backtest.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun BacktestDisclaimer() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f))
    ) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Top) {
            Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
            Text(
                "Past performance is not indicative of future results. Backtest results use simulated market conditions.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
