package com.gtismartmoneytrader.presentation.ui.home

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gtismartmoneytrader.domain.model.*
import com.gtismartmoneytrader.presentation.components.*
import com.gtismartmoneytrader.presentation.ui.theme.GTIColors
import com.gtismartmoneytrader.presentation.viewmodel.HomeUiState
import com.gtismartmoneytrader.presentation.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToSettings: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "GTI Smart Money",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Symbol Selector and Status
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SymbolSelector(
                    selectedSymbol = uiState.selectedSymbol,
                    onSymbolSelected = { viewModel.selectSymbol(it) }
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MarketStatusBadge(status = uiState.marketStatus)
                    TradingStatusBadge(status = uiState.tradingStatus)
                }
            }

            // Filter Message Banner
            uiState.filterMessage?.let { message ->
                FilterMessageBanner(
                    message = message,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // Market Data Header
            uiState.marketData?.let { data ->
                MarketDataHeader(
                    marketData = data,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // GTI Chart
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "GTI Chart",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                GTICandleChart(
                    candles = uiState.candles,
                    signals = uiState.signals
                )
                
                CandleLegend()
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Recent Signals
            if (uiState.signals.isNotEmpty()) {
                RecentSignalsSection(
                    signals = uiState.signals.take(5),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Active Signal Panel
            SignalPanel(
                signal = uiState.activeSignal,
                onDismiss = { viewModel.dismissSignal() },
                onAcknowledge = { viewModel.acknowledgeSignal() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Disclaimer
            DisclaimerBanner(
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun MarketDataHeader(
    marketData: MarketData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = marketData.symbol.displayName,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "₹${String.format("%.2f", marketData.ltp)}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    val changeColor = if (marketData.change >= 0) GTIColors.BuyCall else GTIColors.BuyPut
                    val changeIcon = if (marketData.change >= 0) Icons.Default.TrendingUp else Icons.Default.TrendingDown
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = changeIcon,
                            contentDescription = null,
                            tint = changeColor,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "${if (marketData.change >= 0) "+" else ""}${String.format("%.2f", marketData.change)}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = changeColor
                        )
                    }
                    Text(
                        text = "(${if (marketData.changePercent >= 0) "+" else ""}${String.format("%.2f", marketData.changePercent)}%)",
                        style = MaterialTheme.typography.bodySmall,
                        color = changeColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PriceItem(label = "Open", value = String.format("%.2f", marketData.ohlc.open))
                PriceItem(label = "High", value = String.format("%.2f", marketData.ohlc.high))
                PriceItem(label = "Low", value = String.format("%.2f", marketData.ohlc.low))
                PriceItem(label = "Vol", value = formatVolume(marketData.volume))
                PriceItem(label = "ATR", value = String.format("%.2f", marketData.atr))
            }
        }
    }
}

@Composable
private fun PriceItem(
    label: String,
    value: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun RecentSignalsSection(
    signals: List<Signal>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Recent Signals",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        signals.forEach { signal ->
            SignalListItem(signal = signal)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun SignalListItem(
    signal: Signal
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SignalTypeBadge(type = signal.type)
                Column {
                    Text(
                        text = "${signal.strike} ${signal.optionType}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = formatTimestamp(signal.timestamp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            ConfidenceBadge(confidence = signal.confidence)
        }
    }
}

@Composable
private fun DisclaimerBanner(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(20.dp)
            )
            Column {
                Text(
                    text = "Risk Disclaimer",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "This app provides trading signals for informational purposes only. It does not constitute financial advice. Trading in options involves substantial risk of loss.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun formatVolume(volume: Long): String {
    return when {
        volume >= 10000000 -> String.format("%.1fCr", volume / 10000000.0)
        volume >= 100000 -> String.format("%.1fL", volume / 100000.0)
        volume >= 1000 -> String.format("%.1fK", volume / 1000.0)
        else -> volume.toString()
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val sdf = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(timestamp))
}
