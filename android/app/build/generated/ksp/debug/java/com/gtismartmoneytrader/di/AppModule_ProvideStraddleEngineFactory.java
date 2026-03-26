package com.gtismartmoneytrader.di;

import com.gtismartmoneytrader.domain.engine.GTIIndicatorEngine;
import com.gtismartmoneytrader.domain.engine.StraddleEngine;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideStraddleEngineFactory implements Factory<StraddleEngine> {
  private final Provider<GTIIndicatorEngine> gtiEngineProvider;

  public AppModule_ProvideStraddleEngineFactory(Provider<GTIIndicatorEngine> gtiEngineProvider) {
    this.gtiEngineProvider = gtiEngineProvider;
  }

  @Override
  public StraddleEngine get() {
    return provideStraddleEngine(gtiEngineProvider.get());
  }

  public static AppModule_ProvideStraddleEngineFactory create(
      Provider<GTIIndicatorEngine> gtiEngineProvider) {
    return new AppModule_ProvideStraddleEngineFactory(gtiEngineProvider);
  }

  public static StraddleEngine provideStraddleEngine(GTIIndicatorEngine gtiEngine) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideStraddleEngine(gtiEngine));
  }
}
