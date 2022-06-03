package com.example.data.database

import androidx.room.*
import com.example.domain.entities.HabitType
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("select * from Habits")
    fun getAll(): Flow<List<HabitEntity>>

    @Query("select * from Habits where type == :type")
    fun getAll(type: HabitType): Flow<List<HabitEntity>>

    @Query("select * from Habits where id == :id")
    fun getHabit(id: String): Flow<HabitEntity>

    @Query("select * from Habits where id == :id")
    suspend fun checkExistHabit(id: String): HabitEntity?

    @Delete
    suspend fun delete(habit: HabitEntity)

    @Update
    suspend fun update(habit: HabitEntity)

    @Insert
    suspend fun insert(habit: HabitEntity)

    @Query("delete from Habits")
    suspend fun deleteAll()
}