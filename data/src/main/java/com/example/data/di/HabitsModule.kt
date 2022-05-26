package com.example.data.di

import android.content.Context
import com.example.data.database.AppDatabase
import com.example.data.database.HabitDao
import com.example.data.repository.HabitRepositoryImpl
import com.example.domain.repository.HabitRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HabitsModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideRepository(habitDao: HabitDao): HabitRepository {
        return HabitRepositoryImpl(habitDao)
    }

    @Singleton
    @Provides
    fun provideDao(database: AppDatabase): HabitDao {
        return database.habitDao()
    }

    @Singleton
    @Provides
    fun provideDataBase(): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideContext() = context
}