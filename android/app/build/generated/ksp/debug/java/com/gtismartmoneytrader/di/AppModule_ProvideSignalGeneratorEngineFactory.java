package com.gtismartmoneytrader.di;

import com.gtismartmoneytrader.domain.engine.GTIIndicatorEngine;
import com.gtismartmoneytrader.domain.engine.SignalGeneratorEngine;
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
public final class AppModule_ProvideSignalGeneratorEngineFactory implements Factory<SignalGeneratorEngine> {
  private final Provider<GTIIndicatorEngine> gtiEngineProvider;

  public AppModule_ProvideSignalGeneratorEngineFactory(
      Provider<GTIIndicatorEngine> gtiEngineProvider) {
    this.gtiEngineProvider = gtiEngineProvider;
  }

  @Override
  public SignalGeneratorEngine get() {
    return provideSignalGeneratorEngine(gtiEngineProvider.get());
  }

  public static AppModule_ProvideSignalGeneratorEngineFactory create(
      Provider<GTIIndicatorEngine> gtiEngineProvider) {
    return new AppModule_ProvideSignalGeneratorEngineFactory(gtiEngineProvider);
  }

  public static SignalGeneratorEngine provideSignalGeneratorEngine(GTIIndicatorEngine gtiEngine) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideSignalGeneratorEngine(gtiEngine));
  }
}
