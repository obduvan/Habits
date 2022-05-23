package com.example.habits.utils

import android.app.Application
import android.content.Context
import com.example.habits.repository.HabitRepository
import com.example.habits.repository.IHabitRepository
import com.example.data.room.AppDatabase

class App : Application() {

    val database by lazy {  com.example.data.room.AppDatabase.getDatabase(this)}
    val repository: IHabitRepository by lazy { HabitRepository(database.habitDao()) }

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