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
public final class FakeSignalFilter_Factory implements Factory<FakeSignalFilter> {
  private final Provider<GTIIndicatorEngine> gtiEngineProvider;

  public FakeSignalFilter_Factory(Provider<GTIIndicatorEngine> gtiEngineProvider) {
    this.gtiEngineProvider = gtiEngineProvider;
  }

  @Override
  public FakeSignalFilter get() {
    return newInstance(gtiEngineProvider.get());
  }

  public static FakeSignalFilter_Factory create(Provider<GTIIndicatorEngine> gtiEngineProvider) {
    return new FakeSignalFilter_Factory(gtiEngineProvider);
  }

  public static FakeSignalFilter newInstance(GTIIndicatorEngine gtiEngine) {
    return new FakeSignalFilter(gtiEngine);
  }
}
