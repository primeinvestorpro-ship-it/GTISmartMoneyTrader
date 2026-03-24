package com.gtismartmoneytrader.di;

import com.gtismartmoneytrader.domain.engine.OptionSuggestionEngine;
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
public final class AppModule_ProvideOptionSuggestionEngineFactory implements Factory<OptionSuggestionEngine> {
  @Override
  public OptionSuggestionEngine get() {
    return provideOptionSuggestionEngine();
  }

  public static AppModule_ProvideOptionSuggestionEngineFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static OptionSuggestionEngine provideOptionSuggestionEngine() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideOptionSuggestionEngine());
  }

  private static final class InstanceHolder {
    private static final AppModule_ProvideOptionSuggestionEngineFactory INSTANCE = new AppModule_ProvideOptionSuggestionEngineFactory();
  }
}
