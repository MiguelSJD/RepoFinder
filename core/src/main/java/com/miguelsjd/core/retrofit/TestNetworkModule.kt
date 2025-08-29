package com.miguelsjd.core.retrofit

import androidx.annotation.VisibleForTesting
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
object TestNetworkModule {
    val json = Json { ignoreUnknownKeys = true }
    private val contentType = "application/json".toMediaType()

    fun retrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClient())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
}
