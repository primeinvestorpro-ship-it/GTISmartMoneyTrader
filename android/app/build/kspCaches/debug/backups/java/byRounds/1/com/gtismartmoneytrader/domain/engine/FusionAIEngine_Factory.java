package com.gtismartmoneytrader.domain.engine;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class FusionAIEngine_Factory implements Factory<FusionAIEngine> {
  @Override
  public FusionAIEngine get() {
    return newInstance();
  }

  public static FusionAIEngine_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FusionAIEngine newInstance() {
    return new FusionAIEngine();
  }

  private static final class InstanceHolder {
    private static final FusionAIEngine_Factory INSTANCE = new FusionAIEngine_Factory();
  }
}
