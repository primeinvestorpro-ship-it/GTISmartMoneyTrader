package com.gtismartmoneytrader.domain.usecase;

import com.gtismartmoneytrader.domain.engine.FakeSignalFilter;
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
public final class CheckFiltersUseCase_Factory implements Factory<CheckFiltersUseCase> {
  private final Provider<FakeSignalFilter> fakeSignalFilterProvider;

  public CheckFiltersUseCase_Factory(Provider<FakeSignalFilter> fakeSignalFilterProvider) {
    this.fakeSignalFilterProvider = fakeSignalFilterProvider;
  }

  @Override
  public CheckFiltersUseCase get() {
    return newInstance(fakeSignalFilterProvider.get());
  }

  public static CheckFiltersUseCase_Factory create(
      Provider<FakeSignalFilter> fakeSignalFilterProvider) {
    return new CheckFiltersUseCase_Factory(fakeSignalFilterProvider);
  }

  public static CheckFiltersUseCase newInstance(FakeSignalFilter fakeSignalFilter) {
    return new CheckFiltersUseCase(fakeSignalFilter);
  }
}
