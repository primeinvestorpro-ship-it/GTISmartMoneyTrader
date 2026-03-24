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
public final class GTIIndicatorEngine_Factory implements Factory<GTIIndicatorEngine> {
  @Override
  public GTIIndicatorEngine get() {
    return newInstance();
  }

  public static GTIIndicatorEngine_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static GTIIndicatorEngine newInstance() {
    return new GTIIndicatorEngine();
  }

  private static final class InstanceHolder {
    private static final GTIIndicatorEngine_Factory INSTANCE = new GTIIndicatorEngine_Factory();
  }
}
