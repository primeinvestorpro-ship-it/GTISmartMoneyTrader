package com.gtismartmoneytrader.presentation.ui.dashboard

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gtismartmoneytrader.domain.model.*
import com.gtismartmoneytrader.presentation.ui.theme.GTIColors
import com.gtismartmoneytrader.presentation.viewmodel.HomeViewModel

/**
 * Dashboard Screen – the Fusion AI "Master Brain" view.
 * Shows Market State, Fusion Signal Panel, and quick stats.
 */
@Composable
fun DashboardScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // Market State Header
        MarketStatePanel(
            mode = uiState.marketMode,
            marketData = uiState.marketData,
            adx = uiState.adx,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Fusion Signal Panel
        FusionSignalPanel(
            decision = uiState.fusionDecision,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        // Quick Stats Row
        uiState.marketData?.let { data ->
            QuickStatsRow(
                data = data,
                straddleResult = uiState.straddleResult,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Symbol Selector
        SymbolSelectorRow(
            selected = uiState.selectedSymbol,
            onSelect = { viewModel.selectSymbol(it) },
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// ---------------------------------------------------------------------------
// Market State Panel
// ---------------------------------------------------------------------------
@Composable
fun MarketStatePanel(
    mode: MarketMode,
    marketData: MarketData?,
    adx: Double,
    modifier: Modifier = Modifier
) {
    val gradientColors = when (mode) {
        MarketMode.TRENDING -> listOf(GTIColors.TrendingGradientStart, GTIColors.TrendingGradientEnd)
        MarketMode.NEUTRAL -> listOf(Color(0xFF1A237E), Color(0xFF283593))
        MarketMode.VOLATILE -> listOf(GTIColors.ConflictGradientStart, GTIColors.ConflictGradientEnd)
    }

    val modeLabel = when (mode) {
        MarketMode.TRENDING -> "🟢 TRENDING"
        MarketMode.NEUTRAL -> "🟡 NEUTRAL"
        MarketMode.VOLATILE -> "🔴 VOLATILE"
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(gradientColors))
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "Market Mode",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = modeLabel,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    marketData?.let { data ->
                        StatChip(label = "LTP", value = "₹${String.format("%.0f", data.ltp)}")
                        StatChip(
                            label = "Change",
                            value = "${if (data.changePercent >= 0) "+" else ""}${String.format("%.2f", data.changePercent)}%"
                        )
                    }
                    if (adx > 0) StatChip(label = "ADX", value = String.format("%.1f", adx))
                }
            }
        }
    }
}

@Composable
private fun StatChip(label: String, value: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.65f))
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

// ---------------------------------------------------------------------------
// Fusion Signal Panel – 3 cases
// ---------------------------------------------------------------------------
@Composable
fun FusionSignalPanel(
    decision: FusionDecision?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Hub,
                    contentDescription = null,
                    tint = GTIColors.FusionBuy,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    "Decision Fusion AI",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (decision == null) {
                NoTradeContent(reason = "Awaiting market data…")
                return@Column
            }

            when (decision.action) {
                FusionAction.OPTION_BUY -> OptionBuyContent(decision)
                FusionAction.SELL_STRADDLE -> StraddleActiveContent(decision)
                FusionAction.AVOID_CONFLICT -> ConflictContent(decision)
                FusionAction.NO_TRADE -> NoTradeContent(decision.reasoning)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Confidence bar
            ConfidenceBar(score = decision.confidenceScore)

            // Reasoning text
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = decision.reasoning,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun OptionBuyContent(decision: FusionDecision) {
    val isBull = decision.gtiSignal == SignalType.BUY_CALL
    val color = if (isBull) GTIColors.BuyCall else GTIColors.BuyPut
    val label = if (isBull) "BUY CALL 🔵" else "BUY PUT ⚫"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("Signal", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(label, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold, color = color)
            decision.recommendedStrike?.let {
                Text("Strike: $it ATM", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Column(horizontalAlignment = Alignment.End) {
            Text("Momentum", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(decision.gtiStrength.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = color)
        }
    }
}

@Composable
private fun StraddleActiveContent(decision: FusionDecision) {
    val straddle = decision.straddleResult
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(GTIColors.StraddleActive.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("Strategy", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("SHORT STRADDLE 📊", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = GTIColors.StraddleActive)
            straddle?.let {
                Text("₹${String.format("%.0f", it.expectedRangeLow)} – ₹${String.format("%.0f", it.expectedRangeHigh)}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Column(horizontalAlignment = Alignment.End) {
            Text("PoP", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("${String.format("%.0f", straddle?.probabilityOfProfit ?: 0.0)}%", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold, color = GTIColors.StraddleActive)
            straddle?.let {
                Text("Premium ₹${String.format("%.0f", it.totalPremium)}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun ConflictContent(decision: FusionDecision) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(GTIColors.Volatile.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Warning, contentDescription = null, tint = GTIColors.Volatile, modifier = Modifier.size(32.dp))
        Column {
            Text("⚠️ CONFLICT DETECTED", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = GTIColors.Volatile)
            Text("Avoid Trade – GTI + IV conflict", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun NoTradeContent(reason: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(GTIColors.NoTrade.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Block, contentDescription = null, tint = GTIColors.NoTrade, modifier = Modifier.size(32.dp))
        Column {
            Text("❌ NO TRADE ZONE", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = GTIColors.NoTrade)
            Text(reason, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)
        }
    }
}

@Composable
private fun ConfidenceBar(score: Int) {
    val color = when {
        score >= 70 -> GTIColors.ConfidenceHigh
        score >= 45 -> GTIColors.ConfidenceMedium
        else -> GTIColors.ConfidenceLow
    }
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Confidence", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("$score%", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = color)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = score / 100f,
            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

// ---------------------------------------------------------------------------
// Quick Stats Row
// ---------------------------------------------------------------------------
@Composable
private fun QuickStatsRow(
    data: MarketData,
    straddleResult: StraddleResult?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        QuickStatCard(label = "ATR", value = String.format("%.0f", data.atr), modifier = Modifier.weight(1f))
        QuickStatCard(label = "IV Rank", value = "${String.format("%.0f", straddleResult?.ivPercentile ?: 50.0)}%", modifier = Modifier.weight(1f))
        QuickStatCard(label = "Premium", value = straddleResult?.let { "₹${String.format("%.0f", it.totalPremium)}" } ?: "N/A", modifier = Modifier.weight(1f))
    }
}

@Composable
private fun QuickStatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        }
    }
}

// ---------------------------------------------------------------------------
// Symbol Selector Row
// ---------------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SymbolSelectorRow(
    selected: Symbol,
    onSelect: (Symbol) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Symbol.entries.forEach { symbol ->
            val isSelected = symbol == selected
            FilterChip(
                selected = isSelected,
                onClick = { onSelect(symbol) },
                label = { Text(symbol.displayName, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) }
            )
        }
    }
}
