package com.gtismartmoneytrader.domain.usecase;

import com.gtismartmoneytrader.domain.engine.OptionSuggestionEngine;
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
public final class GetOptionSuggestionUseCase_Factory implements Factory<GetOptionSuggestionUseCase> {
  private final Provider<OptionSuggestionEngine> optionEngineProvider;

  public GetOptionSuggestionUseCase_Factory(Provider<OptionSuggestionEngine> optionEngineProvider) {
    this.optionEngineProvider = optionEngineProvider;
  }

  @Override
  public GetOptionSuggestionUseCase get() {
    return newInstance(optionEngineProvider.get());
  }

  public static GetOptionSuggestionUseCase_Factory create(
      Provider<OptionSuggestionEngine> optionEngineProvider) {
    return new GetOptionSuggestionUseCase_Factory(optionEngineProvider);
  }

  public static GetOptionSuggestionUseCase newInstance(OptionSuggestionEngine optionEngine) {
    return new GetOptionSuggestionUseCase(optionEngine);
  }
}
