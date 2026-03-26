package com.gtismartmoneytrader.di;

import com.gtismartmoneytrader.domain.engine.FusionAIEngine;
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
public final class AppModule_ProvideFusionAIEngineFactory implements Factory<FusionAIEngine> {
  @Override
  public FusionAIEngine get() {
    return provideFusionAIEngine();
  }

  public static AppModule_ProvideFusionAIEngineFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FusionAIEngine provideFusionAIEngine() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideFusionAIEngine());
  }

  private static final class InstanceHolder {
    private static final AppModule_ProvideFusionAIEngineFactory INSTANCE = new AppModule_ProvideFusionAIEngineFactory();
  }
}
