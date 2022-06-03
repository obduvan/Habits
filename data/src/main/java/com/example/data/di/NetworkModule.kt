package com.example.data.di

import com.example.data.network.AuthInterceptor
import com.example.data.network.HabitAPI
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val baseUrl = "https://droid-test-server.doubletapp.ru/api/"
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient()
            .newBuilder()
//            .retryOnConnectionFailure(false)
            .addInterceptor(AuthInterceptor())
            .addInterceptor(logInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideNetworkApi(okHttpClient: OkHttpClient): HabitAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(HabitAPI::class.java)
    }
}