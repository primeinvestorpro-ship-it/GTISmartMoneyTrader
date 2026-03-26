package com.gtismartmoneytrader;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.gtismartmoneytrader.data.api.GTIApiService;
import com.gtismartmoneytrader.data.local.GTIDatabase;
import com.gtismartmoneytrader.data.local.dao.CandleDao;
import com.gtismartmoneytrader.data.local.dao.SettingDao;
import com.gtismartmoneytrader.data.local.dao.SignalDao;
import com.gtismartmoneytrader.data.local.dao.TradeDao;
import com.gtismartmoneytrader.data.repository.GTIRepository;
import com.gtismartmoneytrader.data.service.GTIFCMService;
import com.gtismartmoneytrader.data.service.GTIMarketDataService;
import com.gtismartmoneytrader.data.service.GTIMarketDataService_MembersInjector;
import com.gtismartmoneytrader.di.AppModule_ProvideApiServiceFactory;
import com.gtismartmoneytrader.di.AppModule_ProvideBacktestEngineFactory;
import com.gtismartmoneytrader.di.AppModule_ProvideCandleDaoFactory;
import com.gtismartmoneytrader.di.AppModule_ProvideDatabaseFactory;
import com.gtismartmoneytrader.di.AppModule_ProvideFakeSignalFilterFactory;
import com.gtismartmoneytrader.di.AppModule_ProvideFusionAIEngineFactory;
import com.gtismartmoneytrader.di.AppModule_ProvideGTIIndicatorEngineFactory;
import com.gtismartmoneytrader.di.AppModule_ProvideOkHttpClientFactory;
import com.gtismartmoneytrader.di.AppModule_ProvideOptionSuggestionEngineFactory;
import com.gtismartmoneytrader.di.AppModule_ProvideRepositoryFactory;
import com.gtismartmoneytrader.di.AppModule_ProvideRetrofitFactory;
import com.gtismartmoneytrader.di.AppModule_ProvideRiskManagementEngineFactory;
import com.gtismartmoneytrader.di.AppModule_ProvideSettingDaoFactory;
import com.gtismartmoneytrader.di.AppModule_ProvideSignalDaoFactory;
import com.gtismartmoneytrader.di.AppModule_ProvideSignalGeneratorEngineFactory;
import com.gtismartmoneytrader.di.AppModule_ProvideStraddleEngineFactory;
import com.gtismartmoneytrader.di.AppModule_ProvideTradeDaoFactory;
import com.gtismartmoneytrader.domain.engine.BacktestEngine;
import com.gtismartmoneytrader.domain.engine.FakeSignalFilter;
import com.gtismartmoneytrader.domain.engine.FusionAIEngine;
import com.gtismartmoneytrader.domain.engine.GTIIndicatorEngine;
import com.gtismartmoneytrader.domain.engine.OptionSuggestionEngine;
import com.gtismartmoneytrader.domain.engine.RiskManagementEngine;
import com.gtismartmoneytrader.domain.engine.SignalGeneratorEngine;
import com.gtismartmoneytrader.domain.engine.StraddleEngine;
import com.gtismartmoneytrader.presentation.MainActivity;
import com.gtismartmoneytrader.presentation.viewmodel.BacktestViewModel;
import com.gtismartmoneytrader.presentation.viewmodel.BacktestViewModel_HiltModules_KeyModule_ProvideFactory;
import com.gtismartmoneytrader.presentation.viewmodel.HomeViewModel;
import com.gtismartmoneytrader.presentation.viewmodel.HomeViewModel_HiltModules_KeyModule_ProvideFactory;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

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
public final class DaggerGTIApplication_HiltComponents_SingletonC {
  private DaggerGTIApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public GTIApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements GTIApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public GTIApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements GTIApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public GTIApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements GTIApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public GTIApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements GTIApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public GTIApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements GTIApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public GTIApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements GTIApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public GTIApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements GTIApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public GTIApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends GTIApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends GTIApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends GTIApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends GTIApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return ImmutableSet.<String>of(BacktestViewModel_HiltModules_KeyModule_ProvideFactory.provide(), HomeViewModel_HiltModules_KeyModule_ProvideFactory.provide());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }
  }

  private static final class ViewModelCImpl extends GTIApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<BacktestViewModel> backtestViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.backtestViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
    }

    @Override
    public Map<String, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return ImmutableMap.<String, javax.inject.Provider<ViewModel>>of("com.gtismartmoneytrader.presentation.viewmodel.BacktestViewModel", ((Provider) backtestViewModelProvider), "com.gtismartmoneytrader.presentation.viewmodel.HomeViewModel", ((Provider) homeViewModelProvider));
    }

    @Override
    public Map<String, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<String, Object>of();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.gtismartmoneytrader.presentation.viewmodel.BacktestViewModel 
          return (T) new BacktestViewModel(singletonCImpl.provideRepositoryProvider.get(), singletonCImpl.provideBacktestEngineProvider.get());

          case 1: // com.gtismartmoneytrader.presentation.viewmodel.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.provideRepositoryProvider.get(), singletonCImpl.provideGTIIndicatorEngineProvider.get(), singletonCImpl.provideSignalGeneratorEngineProvider.get(), singletonCImpl.provideFakeSignalFilterProvider.get(), singletonCImpl.provideRiskManagementEngineProvider.get(), singletonCImpl.provideOptionSuggestionEngineProvider.get(), singletonCImpl.provideStraddleEngineProvider.get(), singletonCImpl.provideFusionAIEngineProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends GTIApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends GTIApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    @Override
    public void injectGTIFCMService(GTIFCMService gTIFCMService) {
    }

    @Override
    public void injectGTIMarketDataService(GTIMarketDataService gTIMarketDataService) {
      injectGTIMarketDataService2(gTIMarketDataService);
    }

    @CanIgnoreReturnValue
    private GTIMarketDataService injectGTIMarketDataService2(GTIMarketDataService instance) {
      GTIMarketDataService_MembersInjector.injectOkHttpClient(instance, singletonCImpl.provideOkHttpClientProvider.get());
      return instance;
    }
  }

  private static final class SingletonCImpl extends GTIApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<Retrofit> provideRetrofitProvider;

    private Provider<GTIApiService> provideApiServiceProvider;

    private Provider<GTIDatabase> provideDatabaseProvider;

    private Provider<SignalDao> provideSignalDaoProvider;

    private Provider<TradeDao> provideTradeDaoProvider;

    private Provider<SettingDao> provideSettingDaoProvider;

    private Provider<CandleDao> provideCandleDaoProvider;

    private Provider<GTIRepository> provideRepositoryProvider;

    private Provider<GTIIndicatorEngine> provideGTIIndicatorEngineProvider;

    private Provider<SignalGeneratorEngine> provideSignalGeneratorEngineProvider;

    private Provider<StraddleEngine> provideStraddleEngineProvider;

    private Provider<BacktestEngine> provideBacktestEngineProvider;

    private Provider<FakeSignalFilter> provideFakeSignalFilterProvider;

    private Provider<RiskManagementEngine> provideRiskManagementEngineProvider;

    private Provider<OptionSuggestionEngine> provideOptionSuggestionEngineProvider;

    private Provider<FusionAIEngine> provideFusionAIEngineProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 3));
      this.provideRetrofitProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonCImpl, 2));
      this.provideApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<GTIApiService>(singletonCImpl, 1));
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<GTIDatabase>(singletonCImpl, 5));
      this.provideSignalDaoProvider = DoubleCheck.provider(new SwitchingProvider<SignalDao>(singletonCImpl, 4));
      this.provideTradeDaoProvider = DoubleCheck.provider(new SwitchingProvider<TradeDao>(singletonCImpl, 6));
      this.provideSettingDaoProvider = DoubleCheck.provider(new SwitchingProvider<SettingDao>(singletonCImpl, 7));
      this.provideCandleDaoProvider = DoubleCheck.provider(new SwitchingProvider<CandleDao>(singletonCImpl, 8));
      this.provideRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<GTIRepository>(singletonCImpl, 0));
      this.provideGTIIndicatorEngineProvider = DoubleCheck.provider(new SwitchingProvider<GTIIndicatorEngine>(singletonCImpl, 10));
      this.provideSignalGeneratorEngineProvider = DoubleCheck.provider(new SwitchingProvider<SignalGeneratorEngine>(singletonCImpl, 11));
      this.provideStraddleEngineProvider = DoubleCheck.provider(new SwitchingProvider<StraddleEngine>(singletonCImpl, 12));
      this.provideBacktestEngineProvider = DoubleCheck.provider(new SwitchingProvider<BacktestEngine>(singletonCImpl, 9));
      this.provideFakeSignalFilterProvider = DoubleCheck.provider(new SwitchingProvider<FakeSignalFilter>(singletonCImpl, 13));
      this.provideRiskManagementEngineProvider = DoubleCheck.provider(new SwitchingProvider<RiskManagementEngine>(singletonCImpl, 14));
      this.provideOptionSuggestionEngineProvider = DoubleCheck.provider(new SwitchingProvider<OptionSuggestionEngine>(singletonCImpl, 15));
      this.provideFusionAIEngineProvider = DoubleCheck.provider(new SwitchingProvider<FusionAIEngine>(singletonCImpl, 16));
    }

    @Override
    public void injectGTIApplication(GTIApplication gTIApplication) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.gtismartmoneytrader.data.repository.GTIRepository 
          return (T) AppModule_ProvideRepositoryFactory.provideRepository(singletonCImpl.provideApiServiceProvider.get(), singletonCImpl.provideSignalDaoProvider.get(), singletonCImpl.provideTradeDaoProvider.get(), singletonCImpl.provideSettingDaoProvider.get(), singletonCImpl.provideCandleDaoProvider.get());

          case 1: // com.gtismartmoneytrader.data.api.GTIApiService 
          return (T) AppModule_ProvideApiServiceFactory.provideApiService(singletonCImpl.provideRetrofitProvider.get());

          case 2: // retrofit2.Retrofit 
          return (T) AppModule_ProvideRetrofitFactory.provideRetrofit(singletonCImpl.provideOkHttpClientProvider.get());

          case 3: // okhttp3.OkHttpClient 
          return (T) AppModule_ProvideOkHttpClientFactory.provideOkHttpClient();

          case 4: // com.gtismartmoneytrader.data.local.dao.SignalDao 
          return (T) AppModule_ProvideSignalDaoFactory.provideSignalDao(singletonCImpl.provideDatabaseProvider.get());

          case 5: // com.gtismartmoneytrader.data.local.GTIDatabase 
          return (T) AppModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 6: // com.gtismartmoneytrader.data.local.dao.TradeDao 
          return (T) AppModule_ProvideTradeDaoFactory.provideTradeDao(singletonCImpl.provideDatabaseProvider.get());

          case 7: // com.gtismartmoneytrader.data.local.dao.SettingDao 
          return (T) AppModule_ProvideSettingDaoFactory.provideSettingDao(singletonCImpl.provideDatabaseProvider.get());

          case 8: // com.gtismartmoneytrader.data.local.dao.CandleDao 
          return (T) AppModule_ProvideCandleDaoFactory.provideCandleDao(singletonCImpl.provideDatabaseProvider.get());

          case 9: // com.gtismartmoneytrader.domain.engine.BacktestEngine 
          return (T) AppModule_ProvideBacktestEngineFactory.provideBacktestEngine(singletonCImpl.provideGTIIndicatorEngineProvider.get(), singletonCImpl.provideSignalGeneratorEngineProvider.get(), singletonCImpl.provideStraddleEngineProvider.get());

          case 10: // com.gtismartmoneytrader.domain.engine.GTIIndicatorEngine 
          return (T) AppModule_ProvideGTIIndicatorEngineFactory.provideGTIIndicatorEngine();

          case 11: // com.gtismartmoneytrader.domain.engine.SignalGeneratorEngine 
          return (T) AppModule_ProvideSignalGeneratorEngineFactory.provideSignalGeneratorEngine(singletonCImpl.provideGTIIndicatorEngineProvider.get());

          case 12: // com.gtismartmoneytrader.domain.engine.StraddleEngine 
          return (T) AppModule_ProvideStraddleEngineFactory.provideStraddleEngine(singletonCImpl.provideGTIIndicatorEngineProvider.get());

          case 13: // com.gtismartmoneytrader.domain.engine.FakeSignalFilter 
          return (T) AppModule_ProvideFakeSignalFilterFactory.provideFakeSignalFilter(singletonCImpl.provideGTIIndicatorEngineProvider.get());

          case 14: // com.gtismartmoneytrader.domain.engine.RiskManagementEngine 
          return (T) AppModule_ProvideRiskManagementEngineFactory.provideRiskManagementEngine();

          case 15: // com.gtismartmoneytrader.domain.engine.OptionSuggestionEngine 
          return (T) AppModule_ProvideOptionSuggestionEngineFactory.provideOptionSuggestionEngine();

          case 16: // com.gtismartmoneytrader.domain.engine.FusionAIEngine 
          return (T) AppModule_ProvideFusionAIEngineFactory.provideFusionAIEngine();

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
