package com.example.data.di

import android.content.Context
import com.example.data.database.AppDatabase
import com.example.data.database.HabitDao
import com.example.data.network.HabitAPI
import com.example.data.repository.HabitRepositoryImpl
import com.example.domain.repository.HabitRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class DataModule {

    @Singleton
    @Provides
    fun provideRepository(habitDao: HabitDao, habitAPI: HabitAPI): HabitRepository {
        return HabitRepositoryImpl(habitDao, habitAPI)
    }

    @Singleton
    @Provides
    fun provideDao(database: AppDatabase): HabitDao {
        return database.habitDao()
    }

    @Singleton
    @Provides
    fun provideDataBase(context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }
}