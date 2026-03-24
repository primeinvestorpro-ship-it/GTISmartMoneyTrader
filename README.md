# GTI Smart Money Trader - Android App

A production-ready Android application for detecting Smart Money activity and providing actionable options trading signals.

## 📱 Project Overview

This app implements the GTI (Global Technical Intelligence) concept - detecting institutional trading activity through candle patterns and generating high-probability options trading signals.

### Key Features

- **GTI Indicator Engine** - Real-time detection of Smart Money activity (Yellow candles), Buying Pressure (Blue candles), and Selling Pressure (Black candles)
- **Smart Signal Engine** - Automated BUY CALL and BUY PUT signal generation based on GTI patterns
- **Option Suggestion Engine** - Automatic ATM strike calculation and option recommendations
- **Risk Management** - Configurable stop loss, target, and trailing stop loss
- **Fake Signal Filter** - ATR, volume, and sideways market filters to reduce false signals
- **Time Filter** - Trading only during optimal market hours

## 🏗️ Architecture

The app follows **Clean Architecture** with **MVVM** pattern:

```
├── data/           # Data layer (API, Room DB, Repositories)
├── domain/         # Business logic (Engines, Use Cases, Models)
├── presentation/   # UI layer (ViewModels, Screens, Components)
└── di/             # Dependency Injection (Hilt modules)
```

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Architecture**: MVVM + Clean Architecture
- **DI**: Hilt
- **Database**: Room
- **Networking**: Retrofit + OkHttp
- **Charts**: Custom Canvas-based GTI chart
- **Real-time**: Firebase Cloud Messaging
- **Background**: Foreground Service for market data

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- JDK 17+
- Android SDK 34
- Kotlin 1.9.20

### Setup

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Create `google-services.json` (Firebase config) - For push notifications
5. Build and run

### Backend Requirements

The app expects a backend API at `https://api.gtismartmoneytrader.com/` with the following endpoints:

- `GET /api/v1/market-data/{symbol}` - Market data
- `GET /api/v1/signals/{symbol}` - Historical signals
- `GET /api/v1/option-chain/{symbol}/{strike}` - Option chain data
- `POST /api/v1/trades/log` - Log trade for tracking

## 📋 GTI Candle Logic

### Yellow Candle (Accumulation)
```
IF close > open AND volume > avg_volume * 1.2 AND (close - low) > (high - close) * 1.5
THEN Smart Money Interest
```

### Blue Candle (Buying Pressure)
```
IF close > open AND body/range > 0.6 AND volume > avg_volume * 1.5
THEN Buying Pressure
```

### Black Candle (Selling Pressure)
```
IF open > close AND body/range > 0.6 AND volume > avg_volume * 1.5
THEN Selling Pressure
```

## 📊 Signal Logic

### BUY CALL Signal
```
IF Yellow candle exists AND price breaks Yellow HIGH AND current candle = Blue
THEN Generate BUY CALL
```

### BUY PUT Signal
```
IF Yellow candle exists AND price breaks Yellow LOW AND current candle = Black
THEN Generate BUY PUT
```

## ⚙️ Configuration

Key settings (adjustable in Settings screen):

| Setting | Default | Description |
|---------|---------|-------------|
| Stop Loss % | 20% | Percentage-based stop loss |
| Target Ratio | 2.0 | Risk:Reward ratio (1:2) |
| ATR Threshold | 50 | Minimum ATR for signals |
| Volume Threshold | 50% | Minimum volume vs average |
| ADX Threshold | 25 | Minimum ADX for trending market |

## 🔒 Security

- API tokens stored in EncryptedSharedPreferences
- Certificate pinning for API connections
- No sensitive data in logs
- User data encrypted at rest

## 📄 License

Proprietary - All rights reserved

## 🤝 Disclaimer

This app is for informational purposes only. Trading in options involves substantial risk of loss. Past performance does not guarantee future results.
