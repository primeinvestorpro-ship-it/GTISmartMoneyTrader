package com.gtismartmoneytrader.domain.usecase;

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
public final class GetMarketDataUseCase_Factory implements Factory<GetMarketDataUseCase> {
  private final Provider<MarketDataRepository> marketDataRepositoryProvider;

  public GetMarketDataUseCase_Factory(Provider<MarketDataRepository> marketDataRepositoryProvider) {
    this.marketDataRepositoryProvider = marketDataRepositoryProvider;
  }

  @Override
  public GetMarketDataUseCase get() {
    return newInstance(marketDataRepositoryProvider.get());
  }

  public static GetMarketDataUseCase_Factory create(
      Provider<MarketDataRepository> marketDataRepositoryProvider) {
    return new GetMarketDataUseCase_Factory(marketDataRepositoryProvider);
  }

  public static GetMarketDataUseCase newInstance(MarketDataRepository marketDataRepository) {
    return new GetMarketDataUseCase(marketDataRepository);
  }
}
