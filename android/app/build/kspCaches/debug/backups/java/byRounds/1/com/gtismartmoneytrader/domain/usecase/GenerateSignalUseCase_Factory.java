package com.gtismartmoneytrader.domain.usecase;

import com.gtismartmoneytrader.domain.engine.FakeSignalFilter;
import com.gtismartmoneytrader.domain.engine.RiskManagementEngine;
import com.gtismartmoneytrader.domain.engine.SignalGeneratorEngine;
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
public final class GenerateSignalUseCase_Factory implements Factory<GenerateSignalUseCase> {
  private final Provider<SignalGeneratorEngine> signalGeneratorProvider;

  private final Provider<FakeSignalFilter> fakeSignalFilterProvider;

  private final Provider<RiskManagementEngine> riskEngineProvider;

  public GenerateSignalUseCase_Factory(Provider<SignalGeneratorEngine> signalGeneratorProvider,
      Provider<FakeSignalFilter> fakeSignalFilterProvider,
      Provider<RiskManagementEngine> riskEngineProvider) {
    this.signalGeneratorProvider = signalGeneratorProvider;
    this.fakeSignalFilterProvider = fakeSignalFilterProvider;
    this.riskEngineProvider = riskEngineProvider;
  }

  @Override
  public GenerateSignalUseCase get() {
    return newInstance(signalGeneratorProvider.get(), fakeSignalFilterProvider.get(), riskEngineProvider.get());
  }

  public static GenerateSignalUseCase_Factory create(
      Provider<SignalGeneratorEngine> signalGeneratorProvider,
      Provider<FakeSignalFilter> fakeSignalFilterProvider,
      Provider<RiskManagementEngine> riskEngineProvider) {
    return new GenerateSignalUseCase_Factory(signalGeneratorProvider, fakeSignalFilterProvider, riskEngineProvider);
  }

  public static GenerateSignalUseCase newInstance(SignalGeneratorEngine signalGenerator,
      FakeSignalFilter fakeSignalFilter, RiskManagementEngine riskEngine) {
    return new GenerateSignalUseCase(signalGenerator, fakeSignalFilter, riskEngine);
  }
}
