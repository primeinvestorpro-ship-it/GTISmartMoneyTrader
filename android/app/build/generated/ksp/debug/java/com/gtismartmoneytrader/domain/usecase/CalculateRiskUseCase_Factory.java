package com.gtismartmoneytrader.domain.usecase;

import com.gtismartmoneytrader.domain.engine.RiskManagementEngine;
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
public final class CalculateRiskUseCase_Factory implements Factory<CalculateRiskUseCase> {
  private final Provider<RiskManagementEngine> riskEngineProvider;

  public CalculateRiskUseCase_Factory(Provider<RiskManagementEngine> riskEngineProvider) {
    this.riskEngineProvider = riskEngineProvider;
  }

  @Override
  public CalculateRiskUseCase get() {
    return newInstance(riskEngineProvider.get());
  }

  public static CalculateRiskUseCase_Factory create(
      Provider<RiskManagementEngine> riskEngineProvider) {
    return new CalculateRiskUseCase_Factory(riskEngineProvider);
  }

  public static CalculateRiskUseCase newInstance(RiskManagementEngine riskEngine) {
    return new CalculateRiskUseCase(riskEngine);
  }
}
