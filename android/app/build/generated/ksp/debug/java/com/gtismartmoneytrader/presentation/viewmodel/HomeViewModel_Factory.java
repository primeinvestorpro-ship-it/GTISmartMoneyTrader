package com.gtismartmoneytrader.presentation.viewmodel;

import com.gtismartmoneytrader.data.repository.GTIRepository;
import com.gtismartmoneytrader.domain.engine.FakeSignalFilter;
import com.gtismartmoneytrader.domain.engine.FusionAIEngine;
import com.gtismartmoneytrader.domain.engine.GTIIndicatorEngine;
import com.gtismartmoneytrader.domain.engine.OptionSuggestionEngine;
import com.gtismartmoneytrader.domain.engine.RiskManagementEngine;
import com.gtismartmoneytrader.domain.engine.SignalGeneratorEngine;
import com.gtismartmoneytrader.domain.engine.StraddleEngine;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<GTIRepository> repositoryProvider;

  private final Provider<GTIIndicatorEngine> gtiEngineProvider;

  private final Provider<SignalGeneratorEngine> signalGeneratorProvider;

  private final Provider<FakeSignalFilter> fakeSignalFilterProvider;

  private final Provider<RiskManagementEngine> riskEngineProvider;

  private final Provider<OptionSuggestionEngine> optionEngineProvider;

  private final Provider<StraddleEngine> straddleEngineProvider;

  private final Provider<FusionAIEngine> fusionAIEngineProvider;

  public HomeViewModel_Factory(Provider<GTIRepository> repositoryProvider,
      Provider<GTIIndicatorEngine> gtiEngineProvider,
      Provider<SignalGeneratorEngine> signalGeneratorProvider,
      Provider<FakeSignalFilter> fakeSignalFilterProvider,
      Provider<RiskManagementEngine> riskEngineProvider,
      Provider<OptionSuggestionEngine> optionEngineProvider,
      Provider<StraddleEngine> straddleEngineProvider,
      Provider<FusionAIEngine> fusionAIEngineProvider) {
    this.repositoryProvider = repositoryProvider;
    this.gtiEngineProvider = gtiEngineProvider;
    this.signalGeneratorProvider = signalGeneratorProvider;
    this.fakeSignalFilterProvider = fakeSignalFilterProvider;
    this.riskEngineProvider = riskEngineProvider;
    this.optionEngineProvider = optionEngineProvider;
    this.straddleEngineProvider = straddleEngineProvider;
    this.fusionAIEngineProvider = fusionAIEngineProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(repositoryProvider.get(), gtiEngineProvider.get(), signalGeneratorProvider.get(), fakeSignalFilterProvider.get(), riskEngineProvider.get(), optionEngineProvider.get(), straddleEngineProvider.get(), fusionAIEngineProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<GTIRepository> repositoryProvider,
      Provider<GTIIndicatorEngine> gtiEngineProvider,
      Provider<SignalGeneratorEngine> signalGeneratorProvider,
      Provider<FakeSignalFilter> fakeSignalFilterProvider,
      Provider<RiskManagementEngine> riskEngineProvider,
      Provider<OptionSuggestionEngine> optionEngineProvider,
      Provider<StraddleEngine> straddleEngineProvider,
      Provider<FusionAIEngine> fusionAIEngineProvider) {
    return new HomeViewModel_Factory(repositoryProvider, gtiEngineProvider, signalGeneratorProvider, fakeSignalFilterProvider, riskEngineProvider, optionEngineProvider, straddleEngineProvider, fusionAIEngineProvider);
  }

  public static HomeViewModel newInstance(GTIRepository repository, GTIIndicatorEngine gtiEngine,
      SignalGeneratorEngine signalGenerator, FakeSignalFilter fakeSignalFilter,
      RiskManagementEngine riskEngine, OptionSuggestionEngine optionEngine,
      StraddleEngine straddleEngine, FusionAIEngine fusionAIEngine) {
    return new HomeViewModel(repository, gtiEngine, signalGenerator, fakeSignalFilter, riskEngine, optionEngine, straddleEngine, fusionAIEngine);
  }
}
