package com.gtismartmoneytrader.data.repository;

import com.gtismartmoneytrader.data.api.GTIApiService;
import com.gtismartmoneytrader.data.local.dao.CandleDao;
import com.gtismartmoneytrader.data.local.dao.SettingDao;
import com.gtismartmoneytrader.data.local.dao.SignalDao;
import com.gtismartmoneytrader.data.local.dao.TradeDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class GTIRepository_Factory implements Factory<GTIRepository> {
  private final Provider<GTIApiService> apiServiceProvider;

  private final Provider<SignalDao> signalDaoProvider;

  private final Provider<TradeDao> tradeDaoProvider;

  private final Provider<SettingDao> settingDaoProvider;

  private final Provider<CandleDao> candleDaoProvider;

  public GTIRepository_Factory(Provider<GTIApiService> apiServiceProvider,
      Provider<SignalDao> signalDaoProvider, Provider<TradeDao> tradeDaoProvider,
      Provider<SettingDao> settingDaoProvider, Provider<CandleDao> candleDaoProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.signalDaoProvider = signalDaoProvider;
    this.tradeDaoProvider = tradeDaoProvider;
    this.settingDaoProvider = settingDaoProvider;
    this.candleDaoProvider = candleDaoProvider;
  }

  @Override
  public GTIRepository get() {
    return newInstance(apiServiceProvider.get(), signalDaoProvider.get(), tradeDaoProvider.get(), settingDaoProvider.get(), candleDaoProvider.get());
  }

  public static GTIRepository_Factory create(Provider<GTIApiService> apiServiceProvider,
      Provider<SignalDao> signalDaoProvider, Provider<TradeDao> tradeDaoProvider,
      Provider<SettingDao> settingDaoProvider, Provider<CandleDao> candleDaoProvider) {
    return new GTIRepository_Factory(apiServiceProvider, signalDaoProvider, tradeDaoProvider, settingDaoProvider, candleDaoProvider);
  }

  public static GTIRepository newInstance(GTIApiService apiService, SignalDao signalDao,
      TradeDao tradeDao, SettingDao settingDao, CandleDao candleDao) {
    return new GTIRepository(apiService, signalDao, tradeDao, settingDao, candleDao);
  }
}
