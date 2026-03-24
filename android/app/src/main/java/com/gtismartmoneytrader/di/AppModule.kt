package com.gtismartmoneytrader.di

import android.content.Context
import androidx.room.Room
import com.gtismartmoneytrader.data.api.GTIApiService
import com.gtismartmoneytrader.data.local.GTIDatabase
import com.gtismartmoneytrader.data.local.dao.*
import com.gtismartmoneytrader.data.repository.GTIRepository
import com.gtismartmoneytrader.domain.engine.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.gtismartmoneytrader.com/"
    
    private const val DATABASE_NAME = "gti_database"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): GTIApiService {
        return retrofit.create(GTIApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GTIDatabase {
        return Room.databaseBuilder(
            context,
            GTIDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideSignalDao(database: GTIDatabase): SignalDao {
        return database.signalDao()
    }

    @Provides
    @Singleton
    fun provideTradeDao(database: GTIDatabase): TradeDao {
        return database.tradeDao()
    }

    @Provides
    @Singleton
    fun provideSettingDao(database: GTIDatabase): SettingDao {
        return database.settingDao()
    }

    @Provides
    @Singleton
    fun provideCandleDao(database: GTIDatabase): CandleDao {
        return database.candleDao()
    }

    @Provides
    @Singleton
    fun provideRepository(
        apiService: GTIApiService,
        signalDao: SignalDao,
        tradeDao: TradeDao,
        settingDao: SettingDao,
        candleDao: CandleDao
    ): GTIRepository {
        return GTIRepository(apiService, signalDao, tradeDao, settingDao, candleDao)
    }

    @Provides
    @Singleton
    fun provideGTIIndicatorEngine(): GTIIndicatorEngine {
        return GTIIndicatorEngine()
    }

    @Provides
    @Singleton
    fun provideSignalGeneratorEngine(gtiEngine: GTIIndicatorEngine): SignalGeneratorEngine {
        return SignalGeneratorEngine(gtiEngine)
    }

    @Provides
    @Singleton
    fun provideRiskManagementEngine(): RiskManagementEngine {
        return RiskManagementEngine()
    }

    @Provides
    @Singleton
    fun provideFakeSignalFilter(gtiEngine: GTIIndicatorEngine): FakeSignalFilter {
        return FakeSignalFilter(gtiEngine)
    }

    @Provides
    @Singleton
    fun provideOptionSuggestionEngine(): OptionSuggestionEngine {
        return OptionSuggestionEngine()
    }
}
