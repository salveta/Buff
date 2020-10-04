package com.buffup.sdk.data.di

import android.app.Application
import com.buffup.sdk.BuildConfig
import com.buffup.sdk.data.api.BuffApi
import com.buffup.sdk.data.datasource.RemoteBuffDataSource
import com.buffup.sdk.data.repository.BuffDataRepository
import com.buffup.sdk.domain.repository.BuffRepository
import com.buffup.sdk.domain.usecase.GetBuff
import com.buffup.sdk.presentation.BuffView
import com.buffup.sdk.presentation.BuffViewViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(
            listOf(
                dataModule,
                buff
            )
        )
    }
}

val buff = module(override = true) {
    factory { BuffViewViewModel(get()) }
    single { GetBuff(get()) }
    single { RemoteBuffDataSource(get()) }
    single<BuffRepository> { BuffDataRepository(get()) }
}

val dataModule = module(override = true) {
    single<CoroutineDispatcher> { Dispatchers.Main }
    single<BuffApi> { get<Retrofit>().create() }

    val apiTimeOutInSeconds = 60L

    single {
        Retrofit.Builder()
            .apply {
                addConverterFactory(GsonConverterFactory.create())
                baseUrl(BuildConfig.API_URL)
                client(get())
            }
            .build()
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(apiTimeOutInSeconds, TimeUnit.SECONDS)
            .readTimeout(apiTimeOutInSeconds, TimeUnit.SECONDS)
            .writeTimeout(apiTimeOutInSeconds, TimeUnit.SECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    factory {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
        return@factory interceptor
    }
}