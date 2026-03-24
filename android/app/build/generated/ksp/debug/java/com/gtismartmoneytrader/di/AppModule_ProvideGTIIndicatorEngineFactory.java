package com.gtismartmoneytrader.di;

import com.gtismartmoneytrader.domain.engine.GTIIndicatorEngine;
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
public final class AppModule_ProvideGTIIndicatorEngineFactory implements Factory<GTIIndicatorEngine> {
  @Override
  public GTIIndicatorEngine get() {
    return provideGTIIndicatorEngine();
  }

  public static AppModule_ProvideGTIIndicatorEngineFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static GTIIndicatorEngine provideGTIIndicatorEngine() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideGTIIndicatorEngine());
  }

  private static final class InstanceHolder {
    private static final AppModule_ProvideGTIIndicatorEngineFactory INSTANCE = new AppModule_ProvideGTIIndicatorEngineFactory();
  }
}
