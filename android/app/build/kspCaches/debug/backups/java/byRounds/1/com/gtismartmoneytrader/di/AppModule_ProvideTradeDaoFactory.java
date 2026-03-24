package com.gtismartmoneytrader.di;

import com.gtismartmoneytrader.data.local.GTIDatabase;
import com.gtismartmoneytrader.data.local.dao.TradeDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideTradeDaoFactory implements Factory<TradeDao> {
  private final Provider<GTIDatabase> databaseProvider;

  public AppModule_ProvideTradeDaoFactory(Provider<GTIDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TradeDao get() {
    return provideTradeDao(databaseProvider.get());
  }

  public static AppModule_ProvideTradeDaoFactory create(Provider<GTIDatabase> databaseProvider) {
    return new AppModule_ProvideTradeDaoFactory(databaseProvider);
  }

  public static TradeDao provideTradeDao(GTIDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideTradeDao(database));
  }
}
