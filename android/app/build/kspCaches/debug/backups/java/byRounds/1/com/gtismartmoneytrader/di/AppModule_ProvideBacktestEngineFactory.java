package com.gtismartmoneytrader.di;

import com.gtismartmoneytrader.domain.engine.BacktestEngine;
import com.gtismartmoneytrader.domain.engine.GTIIndicatorEngine;
import com.gtismartmoneytrader.domain.engine.SignalGeneratorEngine;
import com.gtismartmoneytrader.domain.engine.StraddleEngine;
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
public final class AppModule_ProvideBacktestEngineFactory implements Factory<BacktestEngine> {
  private final Provider<GTIIndicatorEngine> gtiEngineProvider;

  private final Provider<SignalGeneratorEngine> signalGeneratorProvider;

  private final Provider<StraddleEngine> straddleEngineProvider;

  public AppModule_ProvideBacktestEngineFactory(Provider<GTIIndicatorEngine> gtiEngineProvider,
      Provider<SignalGeneratorEngine> signalGeneratorProvider,
      Provider<StraddleEngine> straddleEngineProvider) {
    this.gtiEngineProvider = gtiEngineProvider;
    this.signalGeneratorProvider = signalGeneratorProvider;
    this.straddleEngineProvider = straddleEngineProvider;
  }

  @Override
  public BacktestEngine get() {
    return provideBacktestEngine(gtiEngineProvider.get(), signalGeneratorProvider.get(), straddleEngineProvider.get());
  }

  public static AppModule_ProvideBacktestEngineFactory create(
      Provider<GTIIndicatorEngine> gtiEngineProvider,
      Provider<SignalGeneratorEngine> signalGeneratorProvider,
      Provider<StraddleEngine> straddleEngineProvider) {
    return new AppModule_ProvideBacktestEngineFactory(gtiEngineProvider, signalGeneratorProvider, straddleEngineProvider);
  }

  public static BacktestEngine provideBacktestEngine(GTIIndicatorEngine gtiEngine,
      SignalGeneratorEngine signalGenerator, StraddleEngine straddleEngine) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideBacktestEngine(gtiEngine, signalGenerator, straddleEngine));
  }
}
