package com.gtismartmoneytrader.domain.usecase;

import com.gtismartmoneytrader.domain.engine.GTIIndicatorEngine;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class ProcessCandlesUseCase_Factory implements Factory<ProcessCandlesUseCase> {
  private final Provider<GTIIndicatorEngine> gtiEngineProvider;

  public ProcessCandlesUseCase_Factory(Provider<GTIIndicatorEngine> gtiEngineProvider) {
    this.gtiEngineProvider = gtiEngineProvider;
  }

  @Override
  public ProcessCandlesUseCase get() {
    return newInstance(gtiEngineProvider.get());
  }

  public static ProcessCandlesUseCase_Factory create(
      Provider<GTIIndicatorEngine> gtiEngineProvider) {
    return new ProcessCandlesUseCase_Factory(gtiEngineProvider);
  }

  public static ProcessCandlesUseCase newInstance(GTIIndicatorEngine gtiEngine) {
    return new ProcessCandlesUseCase(gtiEngine);
  }
}
