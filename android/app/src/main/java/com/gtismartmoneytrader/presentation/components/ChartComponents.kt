package com.gtismartmoneytrader.presentation.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.gtismartmoneytrader.domain.model.Candle
import com.gtismartmoneytrader.domain.model.CandleType
import com.gtismartmoneytrader.domain.model.Signal
import com.gtismartmoneytrader.presentation.ui.theme.GTIColors

@Composable
fun GTICandleChart(
    candles: List<Candle>,
    signals: List<Signal> = emptyList(),
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary.toArgb()
    val gridColor = GTIColors.ChartGrid.toArgb()
    val textColor = GTIColors.ChartText.toArgb()

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(GTIColors.ChartBackground, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        if (candles.isEmpty()) return@Canvas

        val chartWidth = size.width - 60.dp.toPx()
        val chartHeight = size.height - 20.dp.toPx()
        val candleWidth = chartWidth / candles.size
        val candleBodyWidth = candleWidth * 0.7f

        // Find price range
        val minPrice = candles.minOf { it.ohlc.low }
        val maxPrice = candles.maxOf { it.ohlc.high }
        val priceRange = maxPrice - minPrice

        // Draw grid lines
        val gridLineCount = 5
        for (i in 0..gridLineCount) {
            val y = chartHeight * i / gridLineCount
            drawLine(
                color = GTIColors.ChartGrid,
                start = Offset(0f, y),
                end = Offset(chartWidth, y),
                strokeWidth = 1f
            )
        }

        // Draw candles
        candles.forEachIndexed { index, candle ->
            val x = index * candleWidth + candleWidth / 2
            val candleColor = getCandleColor(candle.candleType)

            // Calculate Y positions
            val highY = chartHeight - ((candle.ohlc.high - minPrice) / priceRange * chartHeight).toFloat()
            val lowY = chartHeight - ((candle.ohlc.low - minPrice) / priceRange * chartHeight).toFloat()
            val openY = chartHeight - ((candle.ohlc.open - minPrice) / priceRange * chartHeight).toFloat()
            val closeY = chartHeight - ((candle.ohlc.close - minPrice) / priceRange * chartHeight).toFloat()

            // Draw wick
            drawLine(
                color = candleColor,
                start = Offset(x, highY),
                end = Offset(x, lowY),
                strokeWidth = 2f
            )

            // Draw body
            val bodyTop = minOf(openY, closeY)
            val bodyBottom = maxOf(openY, closeY)
            val bodyHeight = maxOf(bodyBottom - bodyTop, 2f)

            drawRect(
                color = candleColor,
                topLeft = Offset(x - candleBodyWidth / 2, bodyTop),
                size = Size(candleBodyWidth, bodyHeight)
            )
        }

        // Draw signal markers
        signals.forEach { signal ->
            val candleIndex = candles.indexOfLast { it.timestamp <= signal.timestamp }
            if (candleIndex >= 0) {
                val candle = candles[candleIndex]
                val x = candleIndex * candleWidth + candleWidth / 2
                val y = chartHeight - ((candle.ohlc.low - minPrice) / priceRange * chartHeight).toFloat() - 15

                val markerColor = if (signal.type == com.gtismartmoneytrader.domain.model.SignalType.BUY_CALL) 
                    GTIColors.BuyCall else GTIColors.BuyPut

                // Draw triangle marker
                val trianglePath = androidx.compose.ui.graphics.Path().apply {
                    moveTo(x, y)
                    lineTo(x - 8, y - 12)
                    lineTo(x + 8, y - 12)
                    close()
                }
                drawPath(trianglePath, markerColor)
            }
        }

        // Draw price labels
        val labelPaint = Paint().apply {
            color = textColor
            textSize = 10.dp.toPx()
            textAlign = Paint.Align.RIGHT
        }

        for (i in 0..gridLineCount) {
            val price = maxPrice - (priceRange * i / gridLineCount)
            val y = chartHeight * i / gridLineCount + 4
            drawContext.canvas.nativeCanvas.drawText(
                String.format("%.0f", price),
                chartWidth + 50,
                y.toFloat(),
                labelPaint
            )
        }
    }
}

private fun getCandleColor(candleType: CandleType): Color {
    return when (candleType) {
        CandleType.YELLOW -> GTIColors.Yellow
        CandleType.BLUE -> GTIColors.Blue
        CandleType.BLACK -> GTIColors.Black
    }
}

@Composable
fun CandleLegend(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        LegendItem(color = GTIColors.Yellow, label = "Smart Money")
        LegendItem(color = GTIColors.Blue, label = "Buying")
        LegendItem(color = GTIColors.Black, label = "Selling")
    }
}

@Composable
private fun LegendItem(
    color: Color,
    label: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, RoundedCornerShape(2.dp))
        )
        androidx.compose.material3.Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
