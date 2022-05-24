package com.example.habits

import android.app.Application
import android.content.Context
import com.example.data.repository.HabitRepositoryImpl
import com.example.data.database.AppDatabase
import com.example.domain.useCases.HabitsUseCase


class App : Application() {

    val database by lazy {  AppDatabase.getDatabase(this)}
    val habitsUseCase: HabitsUseCase by lazy {
        HabitsUseCase(
            HabitRepositoryImpl(database.habitDao())
        )
    }

    companion object {
        private var instance: Application? = null

        fun getInstance(): Application? = instance

        fun getContext(): Context? = instance?.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}