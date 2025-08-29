package com.miguelsjd.details.di

import com.miguelsjd.core.koin.provider.ModuleProvider
import com.miguelsjd.details.presentation.navigation.DetailsFeatureEntry
import com.miguelsjd.details.presentation.viewmodel.DetailsViewModel
import com.miguelsjd.navigation.FeatureEntry
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

@Suppress("Unused")
internal class DetailsModuleProvider : ModuleProvider() {
    override val modules: List<Module>
        get() = listOf(
            presentationModule,
            navigationModule
        )

    private val presentationModule = module {
        viewModel { (repositoryId: Int) ->
            DetailsViewModel(
                repositoryId = repositoryId,
                getRepositoryUseCase = get(),
                uiRepositoryMapper = get()
            )
        }
    }

    private val navigationModule = module {
        single<FeatureEntry>(qualifier = named("details")) {
            DetailsFeatureEntry()
        }
    }
}
