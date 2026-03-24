package com.gtismartmoneytrader.di;

import com.gtismartmoneytrader.domain.engine.RiskManagementEngine;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class AppModule_ProvideRiskManagementEngineFactory implements Factory<RiskManagementEngine> {
  @Override
  public RiskManagementEngine get() {
    return provideRiskManagementEngine();
  }

  public static AppModule_ProvideRiskManagementEngineFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static RiskManagementEngine provideRiskManagementEngine() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideRiskManagementEngine());
  }

  private static final class InstanceHolder {
    private static final AppModule_ProvideRiskManagementEngineFactory INSTANCE = new AppModule_ProvideRiskManagementEngineFactory();
  }
}
