package com.gtismartmoneytrader.di;

import com.gtismartmoneytrader.data.local.GTIDatabase;
import com.gtismartmoneytrader.data.local.dao.CandleDao;
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
public final class AppModule_ProvideCandleDaoFactory implements Factory<CandleDao> {
  private final Provider<GTIDatabase> databaseProvider;

  public AppModule_ProvideCandleDaoFactory(Provider<GTIDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public CandleDao get() {
    return provideCandleDao(databaseProvider.get());
  }

  public static AppModule_ProvideCandleDaoFactory create(Provider<GTIDatabase> databaseProvider) {
    return new AppModule_ProvideCandleDaoFactory(databaseProvider);
  }

  public static CandleDao provideCandleDao(GTIDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideCandleDao(database));
  }
}
