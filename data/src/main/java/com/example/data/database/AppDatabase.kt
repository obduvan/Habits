package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    version = 4,
    entities = [HabitEntity::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao

//    companion object {
//
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                var instance = INSTANCE
//
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        AppDatabase::class.java,
//                        "habits_database",
//                    ).allowMainThreadQueries()
//                        .fallbackToDestructiveMigration()
//                        .build()
//
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//    }
}
