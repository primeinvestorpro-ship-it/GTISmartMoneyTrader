package com.gtismartmoneytrader.presentation.viewmodel;

import com.gtismartmoneytrader.data.repository.GTIRepository;
import com.gtismartmoneytrader.domain.engine.BacktestEngine;
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
public final class BacktestViewModel_Factory implements Factory<BacktestViewModel> {
  private final Provider<GTIRepository> repositoryProvider;

  private final Provider<BacktestEngine> backtestEngineProvider;

  public BacktestViewModel_Factory(Provider<GTIRepository> repositoryProvider,
      Provider<BacktestEngine> backtestEngineProvider) {
    this.repositoryProvider = repositoryProvider;
    this.backtestEngineProvider = backtestEngineProvider;
  }

  @Override
  public BacktestViewModel get() {
    return newInstance(repositoryProvider.get(), backtestEngineProvider.get());
  }

  public static BacktestViewModel_Factory create(Provider<GTIRepository> repositoryProvider,
      Provider<BacktestEngine> backtestEngineProvider) {
    return new BacktestViewModel_Factory(repositoryProvider, backtestEngineProvider);
  }

  public static BacktestViewModel newInstance(GTIRepository repository,
      BacktestEngine backtestEngine) {
    return new BacktestViewModel(repository, backtestEngine);
  }
}
