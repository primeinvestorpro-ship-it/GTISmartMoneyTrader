package com.gtismartmoneytrader.domain.engine;

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
public final class BacktestEngine_Factory implements Factory<BacktestEngine> {
  private final Provider<GTIIndicatorEngine> gtiEngineProvider;

  private final Provider<SignalGeneratorEngine> signalGeneratorProvider;

  private final Provider<StraddleEngine> straddleEngineProvider;

  public BacktestEngine_Factory(Provider<GTIIndicatorEngine> gtiEngineProvider,
      Provider<SignalGeneratorEngine> signalGeneratorProvider,
      Provider<StraddleEngine> straddleEngineProvider) {
    this.gtiEngineProvider = gtiEngineProvider;
    this.signalGeneratorProvider = signalGeneratorProvider;
    this.straddleEngineProvider = straddleEngineProvider;
  }

  @Override
  public BacktestEngine get() {
    return newInstance(gtiEngineProvider.get(), signalGeneratorProvider.get(), straddleEngineProvider.get());
  }

  public static BacktestEngine_Factory create(Provider<GTIIndicatorEngine> gtiEngineProvider,
      Provider<SignalGeneratorEngine> signalGeneratorProvider,
      Provider<StraddleEngine> straddleEngineProvider) {
    return new BacktestEngine_Factory(gtiEngineProvider, signalGeneratorProvider, straddleEngineProvider);
  }

  public static BacktestEngine newInstance(GTIIndicatorEngine gtiEngine,
      SignalGeneratorEngine signalGenerator, StraddleEngine straddleEngine) {
    return new BacktestEngine(gtiEngine, signalGenerator, straddleEngine);
  }
}
