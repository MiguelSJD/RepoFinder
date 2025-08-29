package com.miguelsjd.home.di

import com.miguelsjd.core.koin.provider.ModuleProvider
import com.miguelsjd.home.data.local.DataStoreManager
import com.miguelsjd.home.data.local.SettingsLocalDataSourceImpl
import com.miguelsjd.home.data.repository.SettingsRepositoryImpl
import com.miguelsjd.home.di.HomeModuleProviderConstants.FEATURE_ENTRY_HOME_QUALIFIER
import com.miguelsjd.home.di.HomeModuleProviderConstants.SETTINGS_DATA_STORE_QUALIFIER
import com.miguelsjd.home.domain.repository.SettingsRepository
import com.miguelsjd.home.domain.usecase.FetchSettingsUseCase
import com.miguelsjd.home.domain.usecase.SaveSettingsUseCase
import com.miguelsjd.home.presentation.navigation.HomeFeatureEntry
import com.miguelsjd.home.presentation.viewmodel.HomeViewModel
import com.miguelsjd.navigation.FeatureEntry
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

@Suppress("Unused")
internal class HomeModuleProvider : ModuleProvider() {

    override val modules: List<Module>
        get() = listOf(
            presentationModule,
            domainModule,
            dataModule,
            navigationModule
        )

    private val presentationModule = module {
        viewModel {
            HomeViewModel(
                getRepositoriesUseCase = get(),
                saveSettingsUseCase = get(),
                fetchSettingsUseCase = get(),
                uiRepositoryMapper = get()
            )
        }
    }

    private val domainModule = module {
        single {
            FetchSettingsUseCase(
                repository = get()
            )
        }

        single {
            SaveSettingsUseCase(
                repository = get()
            )
        }
    }

    private val dataModule = module {
        single<DataStoreManager>(qualifier = named(SETTINGS_DATA_STORE_QUALIFIER)) {
            SettingsLocalDataSourceImpl(
                context = get()
            )
        }

        single<SettingsRepository> {
            SettingsRepositoryImpl(
                dataStoreManager = get(named(SETTINGS_DATA_STORE_QUALIFIER))
            )
        }
    }

    private val navigationModule = module {
        single<FeatureEntry>(qualifier = named(FEATURE_ENTRY_HOME_QUALIFIER)) {
            HomeFeatureEntry()
        }
    }
}
