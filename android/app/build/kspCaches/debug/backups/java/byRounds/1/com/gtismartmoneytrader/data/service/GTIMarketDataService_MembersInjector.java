package com.gtismartmoneytrader.data.service;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

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
public final class GTIMarketDataService_MembersInjector implements MembersInjector<GTIMarketDataService> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  public GTIMarketDataService_MembersInjector(Provider<OkHttpClient> okHttpClientProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
  }

  public static MembersInjector<GTIMarketDataService> create(
      Provider<OkHttpClient> okHttpClientProvider) {
    return new GTIMarketDataService_MembersInjector(okHttpClientProvider);
  }

  @Override
  public void injectMembers(GTIMarketDataService instance) {
    injectOkHttpClient(instance, okHttpClientProvider.get());
  }

  @InjectedFieldSignature("com.gtismartmoneytrader.data.service.GTIMarketDataService.okHttpClient")
  public static void injectOkHttpClient(GTIMarketDataService instance, OkHttpClient okHttpClient) {
    instance.okHttpClient = okHttpClient;
  }
}
