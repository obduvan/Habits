package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.AppDatabase
import com.example.data.database.HabitDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDao(database: AppDatabase): HabitDao {
        return database.habitDao()
    }

    @Singleton
    @Provides
    fun provideDataBase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            SERVER_NAME,
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    companion object {
        private const val SERVER_NAME = "habits_database"
    }
}