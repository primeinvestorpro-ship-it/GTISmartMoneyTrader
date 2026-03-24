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
public final class SignalGeneratorEngine_Factory implements Factory<SignalGeneratorEngine> {
  private final Provider<GTIIndicatorEngine> gtiEngineProvider;

  public SignalGeneratorEngine_Factory(Provider<GTIIndicatorEngine> gtiEngineProvider) {
    this.gtiEngineProvider = gtiEngineProvider;
  }

  @Override
  public SignalGeneratorEngine get() {
    return newInstance(gtiEngineProvider.get());
  }

  public static SignalGeneratorEngine_Factory create(
      Provider<GTIIndicatorEngine> gtiEngineProvider) {
    return new SignalGeneratorEngine_Factory(gtiEngineProvider);
  }

  public static SignalGeneratorEngine newInstance(GTIIndicatorEngine gtiEngine) {
    return new SignalGeneratorEngine(gtiEngine);
  }
}
