package com.gtismartmoneytrader.di;

import com.gtismartmoneytrader.data.local.GTIDatabase;
import com.gtismartmoneytrader.data.local.dao.SignalDao;
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
public final class AppModule_ProvideSignalDaoFactory implements Factory<SignalDao> {
  private final Provider<GTIDatabase> databaseProvider;

  public AppModule_ProvideSignalDaoFactory(Provider<GTIDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public SignalDao get() {
    return provideSignalDao(databaseProvider.get());
  }

  public static AppModule_ProvideSignalDaoFactory create(Provider<GTIDatabase> databaseProvider) {
    return new AppModule_ProvideSignalDaoFactory(databaseProvider);
  }

  public static SignalDao provideSignalDao(GTIDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideSignalDao(database));
  }
}
