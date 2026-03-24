package com.gtismartmoneytrader.presentation.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gtismartmoneytrader.domain.model.Symbol
import com.gtismartmoneytrader.domain.model.Timeframe
import com.gtismartmoneytrader.domain.model.UserSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentSettings: UserSettings,
    onSettingsChanged: (UserSettings) -> Unit,
    onNavigateBack: () -> Unit
) {
    var settings by remember { mutableStateOf(currentSettings) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
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
            // Signal Settings
            SettingsSection(title = "Signal Settings") {
                SettingsSlider(
                    label = "Stop Loss %",
                    value = settings.stopLossPercent,
                    onValueChange = { settings = settings.copy(stopLossPercent = it) },
                    valueRange = 10f..30f
                )
                
                SettingsSlider(
                    label = "Target Ratio",
                    value = settings.targetRatio,
                    onValueChange = { settings = settings.copy(targetRatio = it) },
                    valueRange = 1f..4f,
                    valueFormatter = { "1:${String.format("%.1f", it)}" }
                )
            }

            // Risk Management Settings
            SettingsSection(title = "Risk Management") {
                SettingsSwitch(
                    label = "Trailing Stop Loss",
                    description = "Move SL after +20% profit",
                    checked = settings.trailingSLEnabled,
                    onCheckedChange = { settings = settings.copy(trailingSLEnabled = it) }
                )
                
                if (settings.trailingSLEnabled) {
                    SettingsSlider(
                        label = "Trailing SL Threshold",
                        value = settings.trailingSLThreshold,
                        onValueChange = { settings = settings.copy(trailingSLThreshold = it) },
                        valueRange = 10f..30f,
                        valueFormatter = { "+${String.format("%.0f", it)}%" }
                    )
                }
            }

            // Filter Settings
            SettingsSection(title = "Signal Filters") {
                SettingsSlider(
                    label = "ATR Threshold",
                    value = settings.atrThreshold,
                    onValueChange = { settings = settings.copy(atrThreshold = it) },
                    valueRange = 20f..100f
                )
                
                SettingsSlider(
                    label = "Volume Threshold",
                    value = settings.volumeThresholdPercent,
                    onValueChange = { settings = settings.copy(volumeThresholdPercent = it) },
                    valueRange = 30f..80f,
                    valueFormatter = { "${String.format("%.0f", it)}% of avg" }
                )
                
                SettingsSlider(
                    label = "ADX Threshold",
                    value = settings.adxThreshold,
                    onValueChange = { settings = settings.copy(adxThreshold = it) },
                    valueRange = 15f..40f
                )
            }

            // Notification Settings
            SettingsSection(title = "Notifications") {
                SettingsSwitch(
                    label = "GTI Alerts",
                    description = "Smart Money detection alerts",
                    checked = settings.enableGTIAlerts,
                    onCheckedChange = { settings = settings.copy(enableGTIAlerts = it) }
                )
                
                SettingsSwitch(
                    label = "Signal Alerts",
                    description = "BUY CALL/PUT signal alerts",
                    checked = settings.enableSignalAlerts,
                    onCheckedChange = { settings = settings.copy(enableSignalAlerts = it) }
                )
                
                if (settings.enableSignalAlerts) {
                    SettingsSwitch(
                        label = "CALL Alerts",
                        checked = settings.enableCallAlerts,
                        onCheckedChange = { settings = settings.copy(enableCallAlerts = it) }
                    )
                    
                    SettingsSwitch(
                        label = "PUT Alerts",
                        checked = settings.enablePutAlerts,
                        onCheckedChange = { settings = settings.copy(enablePutAlerts = it) }
                    )
                }
            }

            // Trading Mode
            SettingsSection(title = "Trading Mode") {
                SettingsSwitch(
                    label = "Paper Trading",
                    description = "Simulate trades without real money",
                    checked = settings.paperTradingEnabled,
                    onCheckedChange = { settings = settings.copy(paperTradingEnabled = it) }
                )
            }

            // Save Button
            Button(
                onClick = {
                    onSettingsChanged(settings)
                    onNavigateBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Save Settings")
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))
        content()
        Divider(
            modifier = Modifier.padding(top = 16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
private fun SettingsSlider(
    label: String,
    value: Double,
    onValueChange: (Double) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    valueFormatter: ((Double) -> String)? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = valueFormatter?.invoke(value) ?: String.format("%.1f", value),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toDouble()) },
            valueRange = valueRange,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun SettingsSwitch(
    label: String,
    description: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
            description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
