package com.gtismartmoneytrader.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gtismartmoneytrader.domain.model.*
import com.gtismartmoneytrader.presentation.ui.theme.*

@Composable
fun MarketStatusBadge(
    status: MarketStatus,
    modifier: Modifier = Modifier
) {
    val color = when (status) {
        MarketStatus.TRENDING -> GTIColors.Trending
        MarketStatus.SIDEWAYS -> GTIColors.Sideways
    }
    val text = when (status) {
        MarketStatus.TRENDING -> "Trending"
        MarketStatus.SIDEWAYS -> "Sideways"
    }

    Row(
        modifier = modifier
            .background(color.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}

@Composable
fun TradingStatusBadge(
    status: TradingStatus,
    modifier: Modifier = Modifier
) {
    val color = when (status) {
        TradingStatus.OPEN -> GTIColors.BuyCall
        TradingStatus.CLOSED -> GTIColors.BuyPut
    }
    val text = when (status) {
        TradingStatus.OPEN -> "Trading Open"
        TradingStatus.CLOSED -> "Outside Hours"
    }
    val icon = when (status) {
        TradingStatus.OPEN -> Icons.Default.CheckCircle
        TradingStatus.CLOSED -> Icons.Default.AccessTime
    }

    Row(
        modifier = modifier
            .background(color.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(12.dp),
            tint = color
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}

@Composable
fun ConfidenceBadge(
    confidence: ConfidenceLevel,
    modifier: Modifier = Modifier
) {
    val color = when (confidence) {
        ConfidenceLevel.HIGH -> GTIColors.ConfidenceHigh
        ConfidenceLevel.MEDIUM -> GTIColors.ConfidenceMedium
        ConfidenceLevel.LOW -> GTIColors.ConfidenceLow
    }
    val text = confidence.name

    Box(
        modifier = modifier
            .background(color.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
            .border(1.dp, color, RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
fun SignalTypeBadge(
    type: SignalType,
    modifier: Modifier = Modifier
) {
    val color = when (type) {
        SignalType.BUY_CALL -> GTIColors.BuyCall
        SignalType.BUY_PUT -> GTIColors.BuyPut
    }
    val text = when (type) {
        SignalType.BUY_CALL -> "BUY CALL"
        SignalType.BUY_PUT -> "BUY PUT"
    }
    val icon = when (type) {
        SignalType.BUY_CALL -> Icons.Default.TrendingUp
        SignalType.BUY_PUT -> Icons.Default.TrendingDown
    }

    Row(
        modifier = modifier
            .background(color, RoundedCornerShape(4.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = Color.White
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun PriceDisplay(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = valueColor
        )
    }
}

@Composable
fun FilterMessageBanner(
    message: String?,
    modifier: Modifier = Modifier
) {
    if (message != null) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(GTIColors.ConfidenceMedium.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = GTIColors.ConfidenceMedium,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall,
                color = GTIColors.ConfidenceMedium
            )
        }
    }
}

@Composable
fun SymbolSelector(
    selectedSymbol: Symbol,
    onSymbolSelected: (Symbol) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Symbol.values().forEach { symbol ->
            val isSelected = symbol == selectedSymbol
            Surface(
                onClick = { onSymbolSelected(symbol) },
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                modifier = Modifier.padding(2.dp)
            ) {
                Text(
                    text = symbol.displayName,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary 
                            else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}
