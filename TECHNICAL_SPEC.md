# Technical Specifications for GTI Smart Money Trader

## 1. System Architecture

### 1.1 High-Level Components
```
+------------------+     +------------------+     +------------------+
|   Android App    |<--->|   Backend API    |<--->|  Broker APIs     |
| (Kotlin/Native)  |     | (Node.js/Python) |     | (Zerodha/Upstox) |
+------------------+     +------------------+     +------------------+
        ^                         ^                         ^
        |                         |                         |
+------------------+     +------------------+     +------------------+
| Local Storage    |     | Signal Engine    |     | Market Data Feed |
| (Room DB)        |     | (Real-time)      |     | (WebSocket)      |
+------------------+     +------------------+     +------------------+
```

### 1.2 Data Flow
1. Market data received from broker API via WebSocket
2. Data processed by GTI indicator engine in real-time
3. Signals generated based on predefined logic
4. Option data fetched for suggested strikes
5. Results pushed to Android app via Firebase Cloud Messaging (for alerts) and direct API calls (for UI updates)
6. User interactions sent back to backend for logging and analytics

## 2. API Specifications

### 2.1 Backend Endpoints

#### GET /api/v1/market-data/{symbol}
**Description:** Get current market data for an index
**Parameters:**
- symbol: string (NIFTY or BANKNIFTY)
**Response:**
```json
{
  "symbol": "NIFTY",
  "timestamp": "2026-03-24T09:30:00Z",
  "ohlc": {
    "open": 22400.5,
    "high": 22450.2,
    "low": 22390.8,
    "close": 22440.1
  },
  "volume": 1250000,
  "atr": 85.5
}
```

#### GET /api/v1/signals/{symbol}
**Description:** Get latest signals for an index
**Parameters:**
- symbol: string (NIFTY or BANKNIFTY)
**Response:**
```json
{
  "symbol": "NIFTY",
  "timestamp": "2026-03-24T09:30:00Z",
  "signals": [
    {
      "id": "sig_12345",
      "type": "BUY_CALL",
      "confidence": "HIGH",
      "entryPrice": 120.5,
      "stopLoss": 90.4,
      "target": 180.8,
      "strike": 22450,
      "optionType": "CE",
      "candlePattern": {
        "yellowCandle": true,
        "breakoutLevel": 22440.1,
        "currentCandle": "BLUE"
      }
    }
  ]
}
```

#### GET /api/v1/option-chain/{symbol}/{strike}
**Description:** Get option chain data around a specific strike
**Parameters:**
- symbol: string (NIFTY or BANKNIFTY)
- strike: number (ATM strike)
**Response:**
```json
{
  "symbol": "NIFTY",
  "timestamp": "2026-03-24T09:30:00Z",
  "underlyingPrice": 22440.1,
  "atmStrike": 22450,
  "expiry": "2026-03-27",
  "options": [
    {
      "strike": 22400,
      "type": "CE",
      "ltp": 85.2,
      "bid": 84.8,
      "ask": 85.5,
      "volume": 12500,
      "oi": 45000,
      "delta": 0.45,
      "gamma": 0.012,
      "theta": -0.85,
      "vega": 0.32,
      "iv": 12.5
    },
    {
      "strike": 22450,
      "type": "CE",
      "ltp": 120.5,
      "bid": 120.0,
      "ask": 121.0,
      "volume": 18500,
      "oi": 62000,
      "delta": 0.52,
      "gamma": 0.010,
      "theta": -0.92,
      "vega": 0.35,
      "iv": 13.0
    }
  ]
}
```

#### POST /api/v1/trades/log
**Description:** Log a user trade for performance tracking
**Body:**
```json
{
  "signalId": "sig_12345",
  "action": "TAKEN",
  "entryPrice": 121.0,
  "entryTime": "2026-03-24T09:31:00Z",
  "exitPrice": 175.5,
  "exitTime": "2026-03-24T10:15:00Z",
  "pnl": 45.0,
  "pnlPercent": 37.2
}
```
**Response:**
```json
{
  "success": true,
  "tradeId": "trade_67890"
}
```

### 2.2 Data Contracts

#### GTI Candle Types
- YELLOW: Accumulation detected
- BLUE: Strong bullish momentum + volume spike
- BLACK: Strong bearish momentum + volume spike

#### Signal Types
- BUY_CALL: Long call option signal
- BUY_PUT: Long put option signal

#### Confidence Levels
- HIGH: Strong volume, clear candle pattern, optimal time of day
- MEDIUM: Moderate volume, decent pattern, acceptable time
- LOW: Weak volume, ambiguous pattern, suboptimal time

## 3. Android App Specifications

### 3.1 Technology Stack
- Language: Kotlin
- Minimum SDK: 21 (Android 5.0)
- Target SDK: 34 (Android 14)
- Architecture: MVVM with Clean Architecture principles
- Dependency Injection: Hilt
- Networking: Retrofit 2 + OkHttp + Coroutines
- Local Database: Room
- Real-time Updates: Firebase Cloud Messaging (for alerts) + direct socket connection
- Charting: MPAndroidChart or equivalent
- UI: Jetpack Compose (recommended) or XML/ViewBinding

### 3.2 Key Modules
1. **data**: Repository pattern, network models, local database
2. **domain**: Use cases, business logic
3. **presentation**: ViewModels, UI states, navigation
4. **di**: Dependency injection modules
5. **utils**: Helper functions, constants, extensions

### 3.3 Local Database Schema (Room)

#### Signals Table
```sql
CREATE TABLE signals (
    id TEXT PRIMARY KEY,
    symbol TEXT NOT NULL,
    timestamp INTEGER NOT NULL,
    type TEXT NOT NULL,
    confidence TEXT NOT NULL,
    entryPrice REAL NOT NULL,
    stopLoss REAL NOT NULL,
    target REAL NOT NULL,
    strike INTEGER NOT NULL,
    optionType TEXT NOT NULL,
    isAcknowledged INTEGER DEFAULT 0
);
```

#### Trades Table
```sql
CREATE TABLE trades (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    signalId TEXT NOT NULL,
    action TEXT NOT NULL,
    entryPrice REAL,
    entryTime INTEGER,
    exitPrice REAL,
    exitTime INTEGER,
    pnl REAL,
    pnlPercent REAL,
    FOREIGN KEY (signalId) REFERENCES signals(id)
);
```

#### Settings Table
```sql
CREATE TABLE settings (
    key TEXT PRIMARY KEY,
    value TEXT NOT NULL
);
```

### 3.4 Background Services
- Foreground service for maintaining WebSocket connection to broker API
- JobScheduler for periodic data syncs (when app is in background)
- Firebase Message Service for handling push notifications

### 3.5 Security Considerations
- Secure storage of API tokens (EncryptedSharedPreferences or Android Keystore)
- Certificate pinning for API connections
- OAuth2 authentication flow with PKCE for public clients
- Regular security updates for dependencies

## 4. Backend Specifications

### 4.1 Technology Stack
- Language: Node.js (Express) or Python (FastAPI)
- Real-time processing: WebSocket server (Socket.IO or native WebSockets)
- Database: PostgreSQL for persistent data, Redis for caching
- Queue: RabbitMQ or Apache Kafka for signal processing (if needed)
- Deployment: Docker containers orchestrated with Kubernetes or Docker Compose
- Monitoring: Prometheus + Grafana for metrics, ELK stack for logs
- CI/CD: GitHub Actions or GitLab CI

### 4.2 Core Services

#### Market Data Service
- Maintains WebSocket connections to broker APIs
- Normalizes data from different brokers
- Calculates technical indicators (ATR, volume averages, etc.)
- Caches recent market data in Redis

#### GTI Signal Engine Service
- Processes market data in real-time
- Implements GTI candle logic
- Generates signals based on predefined rules
- Applies filters (ATR, volume, time, sideways market)
- Publishes signals to message queue

#### Option Suggestion Service
- Listens for new signals from queue
- Fetches option chain data for suggested ATM strikes
- Calculates Greeks if not provided by broker
- Formats response for mobile app

#### User Trade Service
- Handles trade logging requests
- Updates performance statistics
- Stores user trade data for analytics

#### Notification Service
- Processes signal events
- Sends push notifications via Firebase Cloud Messaging
- Manages user notification preferences

### 4.3 Performance Requirements
- Signal generation latency: < 500ms from market data receipt
- API response time: < 200ms for 95% of requests
- Concurrent users: Support 10,000+ active users
- Data throughput: Handle 50+ market data updates per second per instrument
- Uptime: 99.9% monthly SLA

## 5. Third-Party Integrations

### 5.1 Broker APIs
| Broker | API Type | Auth Method | Rate Limits | Notes |
|--------|----------|-------------|-------------|-------|
| Zerodha Kite | REST/WebSocket | API Key + Access Token | 3 req/sec | Most popular in India |
| Upstox | REST/WebSocket | API Key + Access Token | 5 req/sec | Good documentation |
| Angel One SmartAPI | REST/WebSocket | JWT + API Key | 10 req/sec | Full featured |

### 5.2 External Services
- Firebase Cloud Messaging: Push notifications
- Google Analytics/Firebase Analytics: User behavior tracking
- Sentry/Crashlytics: Error reporting and crash analytics
- Stripe/Razorpay: Payment processing for premium subscriptions
- AWS Twilio/SMS Gateway: Backup notification delivery

## 6. Testing Strategy

### 6.1 Unit Testing
- Target: 80%+ code coverage
- Frameworks: JUnit/Mockito (Android), Jest/Mocha (Backend)
- Test GTI logic with historical data samples
- Test edge cases in signal generation logic

### 6.2 Integration Testing
- Test API contracts with mock broker responses
- Test database operations
- Test WebSocket connections and message handling
- Test notification delivery

### 6.3 End-to-End Testing
- Android: Espresso/UI Automator for critical user flows
- Backend: Supertest/Postman collections for API flows
- Full signal flow: Market data → GTI processing → Signal generation → Option suggestion → App display

### 6.4 Performance Testing
- Load testing with JMeter or k6
- Stress testing for peak market hours (9:15-3:30 IST)
- Memory leak detection in Android app
- Battery consumption profiling

### 6.5 User Acceptance Testing
- Beta test with 50-100 active traders
- Collect feedback on signal accuracy and usability
- Iterate based on real-world usage patterns

## 7. Deployment & DevOps

### 7.1 Environment Strategy
- Development: Individual developer environments
- Staging: Exact replica of production for final testing
- Production: Live environment serving real users

### 7.2 CI/CD Pipeline
1. Code commit to feature branch
2. Automated unit tests on push
3. Merge to develop branch triggers:
   - Integration tests
   - Deploy to staging
   - Smoke tests
4. Release branch creation triggers:
   - Performance tests
   - Security scans
   - Manual approval
   - Deploy to production
   - Post-deployment validation

### 7.3 Infrastructure
- Containerization: Docker
- Orchestration: Kubernetes (EKS/GKE/AKS) or Docker Swarm
- Load Balancing: AWS ALB/Nginx Plus
- Database: Amazon RDS PostgreSQL or self-managed
- Caching: Amazon ElastiCache Redis
- Monitoring: CloudWatch/Prometheus + Grafana
- Logging: CloudWatch Logs/ELK Stack
- CDN: Amazon CloudFront (for any static assets)

## 8. Scalability Considerations

### 8.1 Horizontal Scaling
- Stateless backend services can be scaled behind load balancer
- Redis clustering for shared cache
- Database read replicas for query distribution
- Market data service sharding by instrument

### 8.2 Data Partitioning
- Time-series partitioning for market data in database
- User data partitioning by geographical region (if expanding globally)
- Signal data archiving older than 6 months to cold storage

### 8.3 Caching Strategy
- L1: In-memory caches (Caffeine/Guava) for frequently accessed data
- L2: Redis for shared caching across instances
- Market data: TTL of 1-5 seconds depending on data type
- Option chain data: TTL of 10-30 seconds
- User preferences: Long TTL or permanent with invalidation on change

## 9. Compliance & Legal

### 9.1 Data Privacy
- GDPR-like compliance for user data (if applicable)
- Clear privacy policy detailing data collection and usage
- Ability for users to export/delete their data
- Secure handling of financial information

### 9.2 Financial Regulations
- Disclaimer that app does not provide investment advice
- No execution of trades through the app (information only)
- Clear risk warnings displayed prominently
- Terms of Service covering liability limitations

### 9.3 Intellectual Property
- Proprietary GTI algorithm protected as trade secret
- Open-source components used according to their licenses
- Trademark protection for app name and logo

## 10. Appendices

### 10.1 GTI Indicator Algorithm Details

**Accumulation Detection (Yellow Candle):**
```
IF (close > open) AND 
   (volume > volumeMA(20) * 1.2) AND
   (close - low) > (high - close) * 1.5
THEN yellow = true
```

**Bullish Momentum (Blue Candle):**
```
IF (close > open) AND
   ((close - open) / (high - low)) > 0.6 AND
   (volume > volumeMA(20) * 1.5)
THEN blue = true
```

**Bearish Momentum (Black Candle):**
```
IF (open > close) AND
   ((open - close) / (high - low)) > 0.6 AND
   (volume > volumeMA(20) * 1.5)
THEN black = true
```

### 10.2 Option Greeks Calculation (if needed)
Black-Scholes model implementation for:
- Delta: ∂C/∂S
- Gamma: ∂²C/∂S²
- Theta: ∂C/∂t
- Vega: ∂C/∂σ
- Rho: ∂C/∂r

Where:
- C = Call option price
- S = Underlying price
- t = Time to expiry
- σ = Volatility
- r = Risk-free rate
