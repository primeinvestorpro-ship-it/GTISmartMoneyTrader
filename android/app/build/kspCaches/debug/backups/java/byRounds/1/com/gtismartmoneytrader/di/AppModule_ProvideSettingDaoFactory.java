package com.gtismartmoneytrader.di;

import com.gtismartmoneytrader.data.local.GTIDatabase;
import com.gtismartmoneytrader.data.local.dao.SettingDao;
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
public final class AppModule_ProvideSettingDaoFactory implements Factory<SettingDao> {
  private final Provider<GTIDatabase> databaseProvider;

  public AppModule_ProvideSettingDaoFactory(Provider<GTIDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public SettingDao get() {
    return provideSettingDao(databaseProvider.get());
  }

  public static AppModule_ProvideSettingDaoFactory create(Provider<GTIDatabase> databaseProvider) {
    return new AppModule_ProvideSettingDaoFactory(databaseProvider);
  }

  public static SettingDao provideSettingDao(GTIDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideSettingDao(database));
  }
}
