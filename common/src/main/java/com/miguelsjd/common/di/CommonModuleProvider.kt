package com.miguelsjd.common.di

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.miguelsjd.common.BuildConfig
import com.miguelsjd.common.data.cache.RepositoryMemoryCache
import com.miguelsjd.common.data.db.DatabaseConstants
import com.miguelsjd.common.data.db.RepositoriesDatabase
import com.miguelsjd.common.data.local.RepositoriesLocalDataSource
import com.miguelsjd.common.data.local.RepositoriesLocalDataSourceImpl
import com.miguelsjd.common.data.remote.RepositoriesRemoteDataSource
import com.miguelsjd.common.data.remote.RepositoriesRemoteDataSourceImpl
import com.miguelsjd.common.data.remote.RepositoryRemoteDataSource
import com.miguelsjd.common.data.remote.RepositoryRemoteDataSourceImpl
import com.miguelsjd.common.data.repository.RepositoriesRepositoryImpl
import com.miguelsjd.common.data.repository.RepositoryRepositoryImpl
import com.miguelsjd.common.data.service.CommonService
import com.miguelsjd.common.domain.repository.RepositoriesRepository
import com.miguelsjd.common.domain.repository.RepositoryRepository
import com.miguelsjd.common.domain.usecase.GetRepositoriesUseCase
import com.miguelsjd.common.domain.usecase.GetRepositoriesUseCaseImpl
import com.miguelsjd.common.domain.usecase.GetRepositoryUseCase
import com.miguelsjd.common.domain.usecase.GetRepositoryUseCaseImpl
import com.miguelsjd.common.presentation.mapper.UIRepositoryMapper
import com.miguelsjd.common.presentation.mapper.UIRepositoryMapperImpl
import com.miguelsjd.core.koin.provider.ModuleProvider
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

@Suppress("Unused")
internal class CommonModuleProvider : ModuleProvider() {

    override val modules: List<Module>
        get() = listOf(
            dataModule,
            domainModule,
            networkModule
        )

    private val domainModule = module {
        single<GetRepositoriesUseCase> {
            GetRepositoriesUseCaseImpl(
                repository = get()
            )
        }
        single<GetRepositoryUseCase> {
            GetRepositoryUseCaseImpl(
                repository = get()
            )
        }
        single<UIRepositoryMapper> {
            UIRepositoryMapperImpl()
        }
    }

    private val dataModule = module {
        single {
            RepositoryMemoryCache()
        }
        single<RepositoriesRemoteDataSource> {
            RepositoriesRemoteDataSourceImpl(
                service = get<Retrofit>().create(CommonService::class.java)
            )
        }

        single<RepositoryRemoteDataSource> {
            RepositoryRemoteDataSourceImpl(
                service = get<Retrofit>().create(CommonService::class.java)
            )
        }

        single<RepositoriesLocalDataSource> {
            RepositoriesLocalDataSourceImpl(
                dao = get<RepositoriesDatabase>().repositoryDao()
            )
        }

        single<RepositoriesRepository> {
            RepositoriesRepositoryImpl(
                remoteDataSource = get(),
                localDataSource = get(),
                memoryCache = get()
            )
        }

        single<RepositoryRepository> {
            RepositoryRepositoryImpl(
                remoteDataSource = get(),
                localDataSource = get(),
                memoryCache = get()
            )
        }

        single {
            Room.databaseBuilder(
                androidContext(),
                RepositoriesDatabase::class.java,
                DatabaseConstants.NAME
            ).build()
        }
    }

    private val networkModule = module {
        single {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        }

        single {
            val contentType = "application/json".toMediaType()
            val json = Json {
                ignoreUnknownKeys = true
            }

            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(get())
                .addConverterFactory(json.asConverterFactory(contentType))
                .build()
        }
    }
}
