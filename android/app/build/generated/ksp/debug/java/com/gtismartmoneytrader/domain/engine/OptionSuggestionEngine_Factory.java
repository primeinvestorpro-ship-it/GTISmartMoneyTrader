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
public final class OptionSuggestionEngine_Factory implements Factory<OptionSuggestionEngine> {
  @Override
  public OptionSuggestionEngine get() {
    return newInstance();
  }

  public static OptionSuggestionEngine_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static OptionSuggestionEngine newInstance() {
    return new OptionSuggestionEngine();
  }

  private static final class InstanceHolder {
    private static final OptionSuggestionEngine_Factory INSTANCE = new OptionSuggestionEngine_Factory();
  }
}
