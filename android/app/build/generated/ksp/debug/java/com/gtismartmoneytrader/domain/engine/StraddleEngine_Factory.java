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
public final class StraddleEngine_Factory implements Factory<StraddleEngine> {
  private final Provider<GTIIndicatorEngine> gtiEngineProvider;

  public StraddleEngine_Factory(Provider<GTIIndicatorEngine> gtiEngineProvider) {
    this.gtiEngineProvider = gtiEngineProvider;
  }

  @Override
  public StraddleEngine get() {
    return newInstance(gtiEngineProvider.get());
  }

  public static StraddleEngine_Factory create(Provider<GTIIndicatorEngine> gtiEngineProvider) {
    return new StraddleEngine_Factory(gtiEngineProvider);
  }

  public static StraddleEngine newInstance(GTIIndicatorEngine gtiEngine) {
    return new StraddleEngine(gtiEngine);
  }
}
