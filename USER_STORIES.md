# User Stories for GTI Smart Money Trader

## Epic 1: GTI Indicator Engine

**User Story 1.1: Candle Color Display**
As a trader, I want to see GTI candles displayed in different colors (Yellow, Blue, Black) on my chart so that I can quickly identify Smart Money activity, buying pressure, and selling pressure.

**Acceptance Criteria:**
- Yellow candle displays when accumulation is detected
- Blue candle displays when strong bullish momentum + volume spike occurs
- Black candle displays when strong bearish momentum + volume spike occurs
- Candle colors are clearly visible on the chart
- Candle logic uses OHLC, Volume, and ATR data

**User Story 1.2: GTI Logic Configuration**
As a trader, I want the GTI indicator logic to be based on configurable parameters so that I can adjust sensitivity based on market conditions.

**Acceptance Criteria:**
- Accumulation detection algorithm is implemented
- Bullish momentum detection with volume spike threshold is configurable
- Bearish momentum detection with volume spike threshold is configurable
- ATR calculation uses configurable period
- Parameters can be adjusted in settings (advanced users)

## Epic 2: Smart Signal Engine

**User Story 2.1: CALL Signal Generation**
As a trader, I want to receive BUY CALL signals when the conditions are met so that I can enter long positions with higher probability.

**Acceptance Criteria:**
- Signal triggers when: Yellow candle exists AND price breaks Yellow HIGH AND current candle = Blue
- Signal appears visually on chart with clear marker
- Signal includes confidence level (HIGH/MEDIUM/LOW)
- Signal generation happens in real-time (<1 second delay)

**User Story 2.2: PUT Signal Generation**
As a trader, I want to receive BUY PUT signals when the conditions are met so that I can enter short positions with higher probability.

**Acceptance Criteria:**
- Signal triggers when: Yellow candle exists AND price breaks Yellow LOW AND current candle = Black
- Signal appears visually on chart with clear marker
- Signal includes confidence level (HIGH/MEDIUM/LOW)
- Signal generation happens in real-time (<1 second delay)

## Epic 3: Option Suggestion Engine

**User Story 3.1: ATM Option Display**
As a trader, I want to see the suggested ATM option to trade when a signal is generated so that I know exactly what instrument to trade.

**Acceptance Criteria:**
- When CALL signal: Display ATM CALL option for the index (NIFTY/BANKNIFTY)
- When PUT signal: Display ATM PUT option for the index (NIFTY/BANKNIFTY)
- Display includes: Strike price, LTP, Delta, IV, Expiry
- ATM strike is calculated as nearest strike to current index price
- Data updates in real-time with market

**User Story 3.2: Option Chain Integration**
As a trader, I want to view relevant option chain data for the suggested ATM option so that I can make informed decisions.

**Acceptance Criteria:**
- Option chain data fetched from broker API (Zerodha/Upstox/Angel One)
- Data includes bid/ask, volume, OI, Greeks for strikes around ATM
- User can expand to view full option chain
- Data refreshes at configurable intervals (default: 5 seconds)

## Epic 4: Risk Management Engine

**User Story 4.1: Stop Loss Calculation**
As a trader, I want to see suggested stop loss levels for each signal so that I can manage my risk properly.

**Acceptance Criteria:**
- Stop loss calculated as: 20-25% of option premium OR structure-based (whichever is tighter)
- Structure-based SL uses recent swing low (for CALL) or swing high (for PUT)
- SL price displayed clearly in signal panel
- User can adjust SL percentage in settings (default: 20-25%)

**User Story 4.2: Target Calculation**
As a trader, I want to see suggested target levels based on 1:2 risk-reward ratio so that I can evaluate trade profitability.

**Acceptance Criteria:**
- Target calculated as: 2x risk amount (distance from entry to SL)
- Target price displayed clearly in signal panel
- User can adjust risk-reward ratio in settings (default: 1:2)

**User Story 4.3: Trailing Stop Loss**
As a trader, I want to enable trailing stop loss after a certain profit threshold so that I can lock in gains.

**Acceptance Criteria:**
- Trailing SL activates after +20% profit from entry
- Trailing SL moves up/down as option price moves favorably
- User can enable/disable trailing SL in settings
- User can configure activation threshold (default: +20%)

## Epic 5: Fake Signal Filter

**User Story 5.1: ATR-Based Filter**
As a trader, I want the app to avoid generating signals during low volatility periods so that I reduce false signals.

**Acceptance Criteria:**
- No signals generated when ATR < configurable threshold
- ATR threshold adjustable in settings (default: based on historical volatility)
- Visual indicator when filtering is active (e.g., "Low Volatility - Signals Paused")

**User Story 5.2: Volume Filter**
As a trader, I want the app to avoid generating signals during low volume periods so that I reduce false signals.

**Acceptance Criteria:**
- No signals generated when volume < configurable threshold (percentage of average volume)
- Volume threshold adjustable in settings (default: 50% of 20-period average)
- Visual indicator when filtering is active

**User Story 5.3: Sideways Market Detection**
As a trader, I want the app to avoid generating signals during sideways/choppy markets so that I reduce false signals.

**Acceptance Criteria:**
- No signals generated when market is detected as sideways (ADX < threshold OR price range < threshold)
- Sideways market detection uses configurable parameters
- Visual indicator when filtering is active (e.g., "Sideways Market - Signals Paused")

## Epic 6: Time Filter

**User Story 6.1: Trading Time Windows**
As a trader, I want signals only generated during approved trading times so that I avoid low-momentum periods.

**Acceptance Criteria:**
- Signals only generated between 9:25 AM - 11:30 AM AND 1:30 PM - 3:15 PM
- No signals generated outside these windows (including 11:30 AM - 1:30 PM)
- Time zones configurable (default: IST)
- Visual indicator showing current trading status (e.g., "Trading Window Open" / "Outside Trading Hours")

## Epic 7: UI/UX - Home Screen

**User Story 7.1: Live Index Chart with GTI Candles**
As a trader, I want to see a live chart of the index with GTI-colored candles so that I can visually analyze price action and signals.

**Acceptance Criteria:**
- Real-time chart displaying NIFTY or BANKNIFTY price
- Candles colored according to GTI logic (Yellow/Blue/Black)
- Chart supports pinch-to-zoom and pan
- Chart timeframes: 1min, 5min, 15min, 30min, 1hour
- Default timeframe: 5min

**User Story 7.2: Signal Overlay on Chart**
As a trader, I want to see signals marked directly on the chart so that I can correlate signals with price action.

**Acceptance Criteria:**
- BUY CALL signals marked with green upward arrow below candle
- BUY PUT signals marked with red downward arrow above candle
- Signal markers include timestamp
- Tapping marker shows signal details in tooltip
- Signal markers persist for configurable duration (default: end of day)

**User Story 7.3: Market Status Indicator**
As a trader, I want to see whether the market is trending or sideways so that I can adjust my strategy accordingly.

**Acceptance Criteria:**
- Display shows "Trending" or "Sideways" status
- Status determined by ADX > 25 (trending) or ADX < 20 (sideways) - configurable
- Visual indicator (green dot for trending, yellow dot for sideways)
- Tooltip explains how status is calculated

## Epic 8: UI/UX - Signal Panel

**User Story 8.1: Detailed Signal Display**
As a trader, I want to see all relevant information for a signal in a dedicated panel so that I can make quick trading decisions.

**Acceptance Criteria:**
- Panel shows: Signal type (BUY CALL/PUT), Index, Strike, Entry price, SL, Target, Confidence
- Information clearly labeled and easy to read
- Panel appears when signal is generated
- Panel auto-hides after configurable time (default: 30 seconds) or can be dismissed
- Panel can be pinned to stay visible

**User Story 8.2: Signal Confidence Indicator**
As a trader, I want to see a confidence level for each signal so that I can weigh the reliability of different signals.

**Acceptance Criteria:**
- Confidence level: HIGH, MEDIUM, LOW
- Based on factors: volume strength, candle clarity, time of day, alignment with higher timeframe
- Visual indicator (color-coded: green/yellow/red)
- Tooltip explains factors contributing to confidence score

## Epic 9: Alerts System

**User Story 9.1: Push Notifications for GTI Alerts**
As a trader, I want to receive push notifications for important events so that I don't miss trading opportunities even when not actively watching the app.

**Acceptance Criteria:**
- Notification for: "🟡 GTI Alert: Smart Money Detected" (when yellow candle forms)
- Notification for: "🔵 BUY CALL Signal Confirmed" (when CALL signal triggers)
- Notification for: "⚫ BUY PUT Signal Confirmed" (when PUT signal triggers)
- Notifications respect Android notification channels
- User can enable/disable specific notification types in settings
- Notifications include deep link to open app to signal details

## Epic 10: Performance Dashboard

**User Story 10.1: Trading Statistics**
As a trader, I want to see my performance statistics so that I can evaluate the effectiveness of the GTI strategy over time.

**Acceptance Criteria:**
- Dashboard shows: Win Rate (%), Total Trades, Net Profit/Loss, Accuracy %
- Stats calculated based on signals followed (user must mark trades as taken/missed)
- Profit/Loss calculation assumes entry at signal price, exit at SL/Target (or manual entry)
- Stats resetable (daily/weekly/monthly/all-time)
- Visual charts showing equity curve over time

**User Story 10.2: Trade Logging**
As a trader, I want to log my trades taken based on signals so that I can track my actual performance vs. signal performance.

**Acceptance Criteria:**
- User can mark each signal as: Taken, Missed, Ignored
- For taken trades: User can enter actual entry/exit prices and times
- System calculates actual P&L for taken trades
- Trade log exportable (CSV format)
- Trade history viewable with filters (date, signal type, result)

## Epic 11: Data Integration

**User Story 11.1: Market Data Connection**
As a trader, I want the app to connect to my broker's API so that I can get real-time market data for signal generation.

**Acceptance Criteria:**
- Supports Zerodha Kite API, Upstox API, Angel One SmartAPI (configurable)
- Secure OAuth2 authentication flow
- Real-time WebSocket connection for live data
- Fallback to polling if WebSocket unavailable
- Handles API rate limits gracefully
- Displays connection status (Connected/Disconnected/Error)

**User Story 11.2: Instrument Configuration**
As a trader, I want to choose which index (NIFTY/BANKNIFTY) to trade so that I can focus on my preferred instrument.

**Acceptance Criteria:**
- User can select primary instrument: NIFTY or BANKNIFTY
- Option to add both instruments (switchable)
- All signals, charts, and option suggestions based on selected instrument
- Instrument preference saved in user settings

## Epic 12: Settings & Configuration

**User Story 12.1: User Preferences**
As a trader, I want to customize various parameters so that I can adapt the app to my trading style and risk tolerance.

**Acceptance Criteria:**
- Settings accessible from main menu
- Categories: Signals, Risk Management, Alerts, Display, Data, Account
- Each setting has clear label, description, and default value
- Changes apply immediately or require app restart (clearly indicated)
- Settings persist across app sessions
- Reset to defaults option available

**User Story 12.2: Paper Trading Mode**
As a trader, I want to test the app's signals in a simulated environment so that I can evaluate performance without risking real capital.

**Acceptance Criteria:**
- Paper trading mode toggle in settings
- When enabled: Simulated account balance (default: ₹1,00,000)
- Signals generate simulated trades
- Performance dashboard shows paper trading results
- Separate from live trading statistics
- Easy switch between paper and live modes
