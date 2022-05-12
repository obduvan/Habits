package com.example.habits.net


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {

    private const val baseUrl = "https://droid-test-server.doubletapp.ru/api/"
    val habitAPI: HabitAPI

    init {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient()
            .newBuilder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(logInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        habitAPI = retrofit.create(HabitAPI::class.java)
    }
}