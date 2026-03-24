package com.gtismartmoneytrader.di;

import com.gtismartmoneytrader.domain.engine.FakeSignalFilter;
import com.gtismartmoneytrader.domain.engine.GTIIndicatorEngine;
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
public final class AppModule_ProvideFakeSignalFilterFactory implements Factory<FakeSignalFilter> {
  private final Provider<GTIIndicatorEngine> gtiEngineProvider;

  public AppModule_ProvideFakeSignalFilterFactory(Provider<GTIIndicatorEngine> gtiEngineProvider) {
    this.gtiEngineProvider = gtiEngineProvider;
  }

  @Override
  public FakeSignalFilter get() {
    return provideFakeSignalFilter(gtiEngineProvider.get());
  }

  public static AppModule_ProvideFakeSignalFilterFactory create(
      Provider<GTIIndicatorEngine> gtiEngineProvider) {
    return new AppModule_ProvideFakeSignalFilterFactory(gtiEngineProvider);
  }

  public static FakeSignalFilter provideFakeSignalFilter(GTIIndicatorEngine gtiEngine) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideFakeSignalFilter(gtiEngine));
  }
}
